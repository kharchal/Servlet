package ua.hav.dao;

import org.apache.log4j.Logger;
import ua.hav.domain.Book;
import ua.hav.domain.Genre;
import ua.hav.domain.annotations.*;
import ua.hav.domain.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.List;

/**
 * Created by Юля on 12.08.2016.
 */
public class Dao<T> {
    
    private static Logger logger = Logger.getLogger(Dao.class);
    private Class clazz;
    private String beanName;
    private Field[] fields;
    private Field idField;
    private List<String> fieldNames;
    //SQL queries
    private String FIND_ALL;
    private String FIND_ALL_LIKE;
    private String FIND_ALL_LIKE_END;
    private String FIND;
    private String DELETE;
    private String ADD;
    private String UPDATE;
    private static Map<String, Class> entities;

    static {
        entities = new HashMap<>();
        entities.put("Book", Book.class);
        entities.put("Genre", Genre.class);
        entities.put("User", User.class);
    }

    public static Map<String, Class> getEntities() {
        return entities;
    }

    // creating SQL queries using Reflection
    private void init() {
        fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            fieldNames.add(f.getName());
            if (f.getName().equals("id")) {
                idField = f;
            }
        }
        FIND_ALL = "SELECT * FROM " + beanName + "s";
        String END = " WHERE id=?";
        FIND = FIND_ALL + END;
        FIND_ALL_LIKE = FIND_ALL + " WHERE ";
        FIND_ALL_LIKE_END = " LIKE ?";
        DELETE = "DELETE FROM " + beanName + "s" + END;
        UPDATE = "UPDATE " + beanName + "s SET ";
        ADD = "INSERT INTO " + beanName + "s (";
        for (String s : fieldNames) {
            if (!s.equals("id")) {
                ADD += s + ", ";
                UPDATE += s + "=?, ";
            }
        }
        ADD = ADD.substring(0, ADD.length() - 2);
        UPDATE = UPDATE.substring(0, UPDATE.length() - 2);
        ADD += ") VALUES (";
        for (String s : fieldNames) {
            if (!s.equals("id")) {
                ADD += "?, ";
            }
        }
        ADD = ADD.substring(0, ADD.length() - 2) + ")";
        UPDATE += " WHERE id=? LIMIT 1";

    }


    Dao(Class clazz) {
        this.clazz = clazz;
        this.beanName = clazz.getSimpleName().toLowerCase();
        fieldNames = new LinkedList<>();
        init();
    }

    protected Class getClazz() {
        return clazz;
    }
    
    public synchronized T find(int id) {
        Object result = null;
        try (Connection cn = DAOFactory.getConnection();
                 PreparedStatement srtatement = cn.prepareStatement(FIND);) {
            srtatement.setInt(1, id);
            ResultSet rs = srtatement.executeQuery();
            if (rs.next()) {
                result = receive(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    public synchronized List<T> findAllLike(Map<String, String> search) {
        List<T> list = new ArrayList<>();
        addNewInstanceToList(list);
        if (search.isEmpty()) {
            return list;
        } else {
            try (Connection cn = DAOFactory.getConnection()) {
                String sqlLikeQuery = FIND_ALL_LIKE;
                int cnt = search.size();
                for (String name : search.keySet()) {
                    sqlLikeQuery += name + " ";
                    sqlLikeQuery += FIND_ALL_LIKE_END;
                    if (--cnt != 0) {
                        sqlLikeQuery += " AND ";
                    }
                }
                System.out.println();
                System.out.println("sqlLikeQuery = " + sqlLikeQuery);
                System.out.println();
                PreparedStatement statement = cn.prepareStatement(sqlLikeQuery);
                int i = 1;
                for (String str : search.keySet()) {
                    String s = search.get(str);
                    if (!s.contains("%")) {
                        s = "%" + s + "%";
                    }
                    statement.setString(i++, s);
                }
                System.out.println(statement);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    list.add(receive(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public synchronized List<T> findAllLike(String field, String mask) {
        List<T> list = new ArrayList<>();
        addNewInstanceToList(list);
        if (field == null || field.equals("") || mask == null || mask.equals("")) {
            return list;
        }
        try (Connection cn = DAOFactory.getConnection()) {
            String sqlLikeQuery = FIND_ALL_LIKE + field + FIND_ALL_LIKE_END;
            PreparedStatement statement = cn.prepareStatement(sqlLikeQuery);
            statement.setString(1, mask);
            ResultSet rs = statement.executeQuery();
//            System.out.println(statement + " " + FIND_ALL);

            while (rs.next()) {
                list.add(receive(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public synchronized List<T> findAll() {
        List<T> list = new ArrayList<>();
        logger.debug("...");
        addNewInstanceToList(list);
        try (Connection cn = DAOFactory.getConnection();
             Statement statement = cn.createStatement();
             ResultSet rs = statement.executeQuery(FIND_ALL);) {

            System.out.println(statement + " " + FIND_ALL);

            while (rs.next()) {
                list.add(receive(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void addNewInstanceToList(List<T> list) {
        try {
            list.add((T) clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public synchronized void save(T el) {
        try (Connection cn = DAOFactory.getConnection();
             PreparedStatement statement = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);) {
            insert(statement, el);
            int n = statement.executeUpdate();
            if (n == 1) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                idField.set(el, rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private int insert(PreparedStatement statement, T el) throws IllegalAccessException, SQLException {
        int i = 1;
        for (Field f : fields) {
//            System.out.println("f = " + f);
            f.setAccessible(true);
            if (f.isAnnotationPresent(Reference.class)) {
                try {
                    Object o = f.get(el);
//                    System.out.println("        o = " + o);
                    Field field = o.getClass().getDeclaredField("id");
//                    System.out.println("field = " + field);
                    field.setAccessible(true);
                    int innerId = (int) field.get(o);
                    statement.setInt(i++, innerId);
//                    System.out.println("statement = " + statement);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } else {
                if (!f.getName().equals("id")) {
                    if (f.getType().getSimpleName().equals("int")) {
                        statement.setInt(i++, (int) f.get(el));
                    }
                    if (f.getType().getSimpleName().equals("String")) {
                        statement.setString(i++, (String) f.get(el));
                    }
                }
            }
        }
        return i;
    }

    
    public synchronized void update(T el) {
        try (Connection cn = DAOFactory.getConnection();
             PreparedStatement statement = cn.prepareStatement(UPDATE);) {
            int i = insert(statement, el);
            statement.setInt(i, (int) idField.get(el));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

        
    public synchronized void delete(int id) {
        try (Connection cn = DAOFactory.getConnection();
             PreparedStatement srtatement = cn.prepareStatement(DELETE)) {
            srtatement.setInt(1, id);
            srtatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private T receive(ResultSet rs) throws SQLException {
        Object result = null;
        try {
            result = clazz.newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Reference.class)) {
                    int id = rs.getInt(f.getName());
                    String type = f.getType().getSimpleName();
                    Dao<T> dao = new Dao<>(entities.get(type));
                    T obj = dao.find(id);
                    f.set(result, obj);

                }
                if (f.isAnnotationPresent(ua.hav.domain.annotations.List.class)) {
                    Class clazz = f.getAnnotation(ua.hav.domain.annotations.List.class).clazz();
                    Dao dao = DAOFactory.getDao(clazz.getSimpleName());
                }
                if (f.getType().getSimpleName().equals("int")) {
                    f.set(result, rs.getInt(f.getName()));
                }
                if (f.getType().getSimpleName().equals("String")){
                    f.set(result, rs.getString(f.getName()));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) result;
    }

}
