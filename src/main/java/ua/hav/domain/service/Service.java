package ua.hav.domain.service;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.annotations.Reference;
import ua.hav.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public class Service {

    public static Map<String, String> get(HttpServletRequest request) {
        Map<String, String> names = new HashMap<>();
        for (Field f : User.class.getDeclaredFields()) {
            String str = request.getParameter(f.getName());
            if (str != null && !str.equals("")) {

                names.put(f.getName(), str);
            }
        }
        return names;
    }

    public static Object get(HttpServletRequest request, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Object obj = null;
        try {
            obj = clazz.newInstance();
            System.out.println("fields = " + fields);
            for (Field f : fields) {
                f.setAccessible(true);
                String name = f.getName();
                String type = f.getType().getSimpleName();
//                System.out.println("name = " + name);
//                System.out.println("type = " + type);
                if (f.isAnnotationPresent(Reference.class)) {


                    int id = Integer.parseInt(request.getParameter(name));
                    f.set(obj, DAOFactory.getDao(type).find(id));
                }
                if (type.equals("String")) {
                    f.set(obj, request.getParameter(name));
                }
                if (type.equals("int")) {
                    String val = request.getParameter(name);
                    if (val == null) {
                        val = "0";
                    }
                    f.set(obj, Integer.parseInt(val));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//        String name = request.getParameter("name");
//        int account = Integer.parseInt(request.getParameter("account"));
//        String id = request.getParameter("id");
//        User user = new User(login, password, name, account);
//        user.setId((id == null) ? 0 : Integer.parseInt(id));
//        System.out.println("obj = " + obj);
        return obj;
    }
}
