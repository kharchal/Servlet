package ua.hav.ui.tags;

import ua.hav.dao.DAOFactory;
import ua.hav.dao.Dao;
import ua.hav.domain.annotations.Role;
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
public class BeanFormTag extends TagSupport {
    private String action = "/Library";
    private String method = "post";
    private Object bean;
    private Map<String, String> errors;
    private String act;
    private JspWriter out;
    private int tabIndent;
    private int id;

    public void setAction(String action) {
        this.action = action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors != null ? errors : new HashMap<String, String>();
    }

    public void setAct(String act) {
        this.act = act;
    }

    @Override
    public int doStartTag() {
        out = pageContext.getOut();
        tabIndent = 0;
        TagUtils util = new TagUtils(out);
        Field[] fields = bean.getClass().getDeclaredFields();
        util.println("");
        util.println("<!--                                             -->");
        util.println("<!-- this form was created by FormTag by (c) Hav -->");
        util.println("<!--                                             -->");
        util.println("");
        util.println(1, "<form action='" + action + "' method='" + method + "'>");
        util.println(1, "<table>");
        tabIndent++;
        for (Field f : fields) {
            String type = f.getType().getSimpleName();
            String name = f.getName();
            f.setAccessible(true);
            util.println(0, "<tr>");
            util.println(1, "<td>" + StringUtil.firtToUpperCase(name) + ": </td>");
            util.print(0, "<td>");
            boolean b = Dao.getEntities().containsKey(type);
            if (b) {
                int selectedId = (int) TagUtils.getFieldValueByNameFromObjectField(f, bean, "id");
                util.println("");
                util.println(1, "<select name='" + name + "'>");
                util.incTabIndent();
//                util.println(1, "");
                List<Object> list = DAOFactory.getDao(type).findAll();
                for (Object el : list) {
                    int id = (int) TagUtils.getFieldValueByName(el, "id");
                    String option = (String) TagUtils.getFieldValueByName(el, "name");
                    util.print(0, "<option value='" + id + "'");
                    if (selectedId == id) {
                        util.print(" selected ");
                    }
                    util.println(">" + option + "</option>");
                }
                util.println(-1, "</select>");
                util.print(0, "<input type='submit' name='command' value='");
                util.print(type + " list'/>");
                util.decTabIndent();
            } else if (f.isAnnotationPresent(Role.class)) {
                try {

                    String role = (String) f.get(bean);
                    util.println("<select name='role' >");
                    util.println("\t<option value='g'" + selected(role, "g") + ">guest</option>");
                    util.println("\t<option value='u'" + selected(role, "u") +">user</option>");
                    util.println("\t<option value='a'" + selected(role, "a")+ ">admin</option>");
                    util.println("</select>");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                util.print("<input type='");
                if (type.equals("String")) {
                    if (name.equals("password")) {
                        util.print(name);
                    } else {
                        util.print("text");
                    }
                } else if (type.equals("int")) {
                    util.print("number");
                }
                util.print("' name='" + name + "' value='");
                if (type.equals("int")) {
                    try {
                        int i = (int)f.get(bean);
                        if (name.equals("id")) {
                            id = i;
                        }
                        util.print(i);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (type.equals("String")) {
                    try {
                        String str = (String) f.get(bean);
                        util.print((str != null) ? str : "");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                util.print("'");
                if (type.equals("int")) {
                    if (name.equals("id")) {
                        util.print(" readonly");
                    } else {
                        util.print(" required");
                    }
                }
                util.print("/>");
            }
            util.println("</td>");
            util.print(0, "<td><div class='red'>");
            String err = errors.get(name);
            util.print(err == null ? "" : err);
            util.println("</div></td>");
            util.println(-1, "</tr>");
        }
        util.println(0, "<tr>");
        util.println(1, "<td colspan='3'>");
        util.println(1, "<input type='submit' name='command' value='" + act + "'/>");
        if (act.toLowerCase().contains("update")) {
            util.print(0, "<input type='button' name='command' value='Delete ");
            util.print(StringUtil.firstToLowerCase(bean.getClass().getSimpleName()));
            util.println("' onclick='check(" + id + ", this.form)'/>");
            util.print(0, "<input type='hidden' name='command' value='Delete ");
            util.println(bean.getClass().getSimpleName().toLowerCase() + "'/>");
        }
        util.println(-1, "</td>");
        util.println(-1, "</tr>");
        util.println(-1, "</table>");
        util.println(-1, "</form>");
        util.println("<form method='post'>");
        util.println(1, "<input type='submit' name='command' value='" + bean.getClass().getSimpleName() + " list'/>");
        util.println("<input type='submit' name='command' value='Main page'/>");
        util.println(-1, "</form>");
        util.println("<script>");
        util.println(1, "function check(i, form){");
        util.println(1, "if (confirm('Confirm deleting element id=' + i)) {");
        util.println(0, "form.submit();");
        util.println(-1, "}");
        util.println(-1, "}");
        util.println(-1, "</script>");
        util.println("<!--                                             -->");
        util.println("<!--   the end of the forn.                      -->");
        util.println("<!--                                             -->");
        util.println("");
        return EVAL_PAGE;
    }

    private String selected(String role, String s) {
        return (role.equals(s)) ? " selected " : "";
    }

}
