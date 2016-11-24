package ua.hav.domain.service;

import ua.hav.domain.Book;
import ua.hav.domain.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Юля on 22.08.2016.
 */
public class MetaData {
    private Class clazz;
    private String className;
    private String tableName;
    private Field[] fields;
    private Map<Field, String> fieldNames;
    private Map<Field,Type> types;
    private Map<Field, String> typeNames;

    MetaData(Class clazz) {
        this.clazz = clazz;
        fieldNames = new HashMap<>();
        typeNames = new HashMap<>();
        types = new HashMap<>();
        init();
    }

    private void init() {
        className = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(Table.class)) {
            tableName = ((Table) clazz.getAnnotation(Table.class)).name();
        } else {
            tableName = className.toLowerCase() + "s";
        }
        fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            fieldNames.put(f, f.getName());
            Class type = f.getType();
            types.put(f, type);

            typeNames.put(f, type.getSimpleName());
        }

    }

    public static void main(String[] args) {
        List<Book> list = new ArrayList<>();
        System.out.println("list = " + list);
        System.out.println("list.getClass().getGenericSuperclass() = " + list.getClass().getGenericSuperclass());
        
        
//        Object o = new MetaData(User.class);
//        System.out.println("o = " + o);
//        Object w = new MetaData(Book.class);
//        System.out.println("w = " + w);
//        Object q = new MetaData(Genre.class);
//        System.out.println("q = " + q);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("MetaData{");
        sb.append("clazz= ").append(clazz).append(System.lineSeparator());
        sb.append("className= '").append(className).append('\'').append(System.lineSeparator());
        sb.append("tableName= '").append(tableName).append('\'').append(System.lineSeparator());
        sb.append("fields: ").append(System.lineSeparator());
        for (Field f : fields) {
            sb.append("\t").append(f.toString()).append(System.lineSeparator());
        }
        sb.append("fieldNames: ").append(System.lineSeparator());
        for (Field f : fieldNames.keySet()) {
            sb.append("\t").append(f.getName()).append(": ")
                    .append(fieldNames.get(f)).append(System.lineSeparator());
        }
        sb.append("types: ").append(System.lineSeparator());
        for (Field f : types.keySet()) {
            sb.append("\t").append(f.getName()).append(": ")
                    .append(types.get(f)).append(System.lineSeparator());
        }
        sb.append("typeNames: ").append(System.lineSeparator());
        for (Field f : typeNames.keySet()) {
            sb.append("\t").append(f.getName()).append(": ")
                    .append(typeNames.get(f)).append(System.lineSeparator());
        }
        sb.append("}");
        return  sb.toString();

    }
}
