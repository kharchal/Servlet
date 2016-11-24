package ua.hav.ui.tags;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by Юля on 12.08.2016.
 */
public class TagUtils {
    private JspWriter out;
    private int tabIndent;

    public TagUtils(JspWriter out) {
        this.out = out;
    }

    public void incTabIndent() {
        tabIndent++;
    }

    public void decTabIndent() {
        tabIndent--;
    }

    public void resetTabIndent() {
        tabIndent = 0;
    }

    public static Object getFieldValueByNameFromObjectField(Field field, Object object, String fieldName) {
        Object result = null;
        try {
            field.setAccessible(true);
            Object o = field.get(object);
            Field f = o.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            result = f.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object getFieldValueByName(Object object, String fieldName) {
        Object result = null;
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            result = f.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void println(String string) {
        print(string);
        print("\r\n");
//     print("<!--         tabIndent=" + tabIndent + "         -->\r\n");
    }

    public void println(int tabsCount, String string) {
        insertTabs(tabsCount);
        println(string);
    }


    public void print(int tabs, String string) {
        insertTabs(tabs);
        print(string);
    }

    public void print(String string) {
        try {
            out.print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(int n) {
        print(String.valueOf(n));
    }


    private void insertTabs(int count) {
        tabIndent += count;
        for (int i = 0 ; i < tabIndent ; i++) {
            print("\t");
        }
    }
}
