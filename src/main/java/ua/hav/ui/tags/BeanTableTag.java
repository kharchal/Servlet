package ua.hav.ui.tags;

import org.apache.log4j.Logger;
import ua.hav.domain.annotations.Display;
import ua.hav.domain.annotations.Reference;
import ua.hav.domain.annotations.Role;
import ua.hav.domain.annotations.ShowByReference;
import ua.hav.domain.utils.StringUtil;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public class BeanTableTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(BeanTableTag.class);
    private List list;
    private Object bean;
    private String beanName;
    private String action;
    private Map<String, String> search;

    public void setSearch(Map<String, String> search) {
            this.search = search;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public int doStartTag(){
        logger.debug("works");
//        beanName = bean.getClass().getSimpleName();
//        System.out.println("beanName = " + beanName);
//        logger.debug("bean=" + bean);
//        logger.debug("beanName= " + beanName);
        JspWriter out = pageContext.getOut();
        TagUtils u = new TagUtils(out);
        if (list == null || list.isEmpty()) {
            u.println("The list is empty!");
        } else {
            Object obj = list.remove(0);
            bean = obj;
            beanName = bean.getClass().getSimpleName();
            Field[] fields = obj.getClass().getDeclaredFields();
            u.resetTabIndent();
            u.println("<!--                                        -->");
            u.println("<!--    this table was created by (c) Hav   -->");
            u.println("<!--                                        -->");
            u.println("<script>");
            u.println(1, "function check(i, form){");
            u.println(1, "if (confirm('Confirm deleting element id=' + i)) {");
            u.println(0, "form.submit();");
            u.println(-1, "}");
            u.println(-1, "}");
            u.println(-1, "</script>");
//            u.println("<form action='" + action + "' method='post'>");
//            u.println(1, "<select name='field'>");
//            u.incTabIndent();
//            for (Field f : fields) {
//                u.println(0, "<option value='" + f.getName() + "'>" + f.getName() + "</option>");
//            }
//            u.println(-1, "</select>");
//            u.println(0, "<input type='text' name='search'/>");
//            u.print(0, "<input type='submit' name='command' value='Search ");
//            String beanName = obj.getClass().getSimpleName().toLowerCase();
//            System.out.println("beanName = " + beanName);
//            u.println(beanName + "'/>");
//            u.println(-1, "</form>");
            u.println("<h3>" + beanName + "s:</h3>");

            u.println("<form action='/Library' method='post'>");
            u.println(1, "<input type='submit' name='command' value='" + beanName + " form'/>");
            u.println(-1, "</form>");
            u.println("<table border='1'>");
//            u.println(1, "<tr>");
//            Field id = null;
//            u.incTabIndent();
//            for (Field f : fields) {
//                f.setAccessible(true);
//                if (f.getName().equals("id")) {
//                    id = f;
//                }
//                u.println(0, "<th>" + StringUtil.firtToUpperCase(f.getName()) + "</th>");
//            }
//            u.println(0, "<th>Action</th>");
//            u.println(-1, "</tr>");
            u.println("<tr>");
            u.println(1, "<form action='" + action + "' method='post'>");
            u.incTabIndent();
            if (search == null) {
                search = new HashMap<>();
            }
            System.out.println("search = " + search);
            for (Field f : fields) {
                u.println(0, "<td>");
                u.print(1, "<input type='text' name='" + f.getName() + "' value='");

//                System.out.println("f.getName() = " + f.getName());
                String value = search.get(f.getName());
                u.print((value == null ? "" : value));


                u.print("' size='");
                int size = 0;
                boolean readonly = false;
                if (f.isAnnotationPresent(Display.class)) {
                    size = f.getAnnotation(Display.class).size();
                    if (!f.getAnnotation(Display.class).show()) {
                        readonly = true;
                    }
                } else {
                    String type = f.getType().getSimpleName();
                    switch (type) {
                        case "int":
                            size = 3;
                            break;
                        case "String":
                            size = 20;
                            break;
                        default:
                            size = 15;
                    }
                }

                u.print(size);
                u.print("'");
                if (readonly) {
                    u.print(" readonly ");
                }
                u.println("/>");
                u.println(-1, "</td>");
            }
            String objectName = obj.getClass().getSimpleName().toLowerCase();
            u.print(0, "<td><input type='submit' name='command' value='Filter ");
            u.println(objectName + "'/>");
            u.println(0, "<input type='submit' name='command' value='ResetFilter " + objectName + "'/></td>");
            u.println(-1, "</form>");
            u.println(-1, "</tr>");
            u.println(0, "<tr>");
            u.println(0, "</tr>");
            u.println(1, "<tr>");
            Field id = null;
            u.incTabIndent();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("id")) {
                    id = f;
                }
                u.println(0, "<th>" + StringUtil.firtToUpperCase(f.getName()) + "</th>");
            }
            u.println(0, "<th>Action</th>");
            u.println(-1, "</tr>");

//            u.incTabIndent();
            for (Object o : list) {
                u.println(0, "<tr>");
                u.incTabIndent();
                int i = 0;
                try {
                    i = (int) id.get(o);
//                    String linkBeg = "<a href='" + action + "?command=Select+" +
//                            StringUtil.firstToLowerCase(o.getClass().getSimpleName()) +
//                            "&id=" + i + "'>";
//                    String linkEnd = "</a>";
                    for (Field f : fields) {
                        u.println(0, "<td align='center'>");
//                        u.println(1, linkBeg);
                        String str = null;
                        if (f.isAnnotationPresent(Reference.class)) {
                            Object object = f.get(o);
                            for (Field fld: object.getClass().getDeclaredFields()) {
                                if (fld.isAnnotationPresent(ShowByReference.class)) {
                                    fld.setAccessible(true);
                                    str = (String) fld.get(object);
                                }
                            }
                            if (str == null) {
                                Field field = object.getClass().getDeclaredField("name");
                                field.setAccessible(true);
                                str = (String) field.get(object);
                            }
                        } else if (f.isAnnotationPresent(Role.class)){
                            String role = (String) f.get(o);
                            switch (role) {
                                case "u" : str = "user"; break;
                                case "g" : str = "guest"; break;
                                case "a" : str = "admin"; break;
                            }
                        } else {
                            Object preCasted = f.get(o);
                            if (f.getType().getSimpleName().equals("int")) {
                                str = String.valueOf((int) preCasted);
                            }
                            if (f.getType().getSimpleName().equals("String")) {
                                str = (String) preCasted;
                            }
                        }
                        if (f.isAnnotationPresent(Display.class)) {
                            if (!f.getAnnotation(Display.class).show()) {
                                str = "*****";
                            }
                        }
                        u.println(1, "" + ((str == null) ? "" : str));


//                        u.println(-1, linkEnd);
                        u.println(-1, "</td>");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                u.println(1, "<form action='" + action + "' method='post' name='form'>");
                u.println(1, "<td>");

                u.println(1, "<input type='hidden' name='id' value=" + i + ">");
//                u.println(0, "<table border='0'><tr><td>");
                String name = o.getClass().getSimpleName().toLowerCase();
                u.print(0, "<input type='submit' name='command' value='Select ");
                u.println(name + "'/>");
                u.print(0, "<input type='submit' name='command' value='Edit ");
                u.println(name + "'/>");
                u.println(0, "<input type='button' value='Delete " + name + "' onclick='check(" + i + ", this.form)'/>");

                u.print(0, "<input type='hidden' name='command' value='Delete ");
                u.println(name + "'/>");
//                u.println(0, "</td></tr></table>");

//                u.print(1, "<a href='" + action + "?command=Edit+");
//                u.print(o.getClass().getSimpleName().toLowerCase());
//                u.println("&id=" + i + "'>Edit</a>");
//                u.print(0, "<a href='" + action + "?command=Delete+");
//                u.print(o.getClass().getSimpleName().toLowerCase());
//                u.println("&id=" + i + "'>Delete</a>");

                u.println(-1, "</td>");
                u.println(-1, "</form>");
                u.println(-1, "</tr>");
            }
            u.println(-1, "</table>");
            u.println("<!--                                        -->");
            u.println("<!--    this table was created by (c) Hav   -->");
            u.println("<!--                                        -->");
        }
        return EVAL_PAGE;
    }
}
