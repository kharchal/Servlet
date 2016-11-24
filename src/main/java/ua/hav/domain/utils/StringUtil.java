package ua.hav.domain.utils;

/**
 * Created by Юля on 12.08.2016.
 */
public class StringUtil {
    public static String firtToUpperCase(String string) {
        if (string.length() == 0) {
            return null;
        }
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }

    public static String firstToLowerCase(String string) {
        if (string.length() == 0) {
            return null;
        }
        return string.substring(0,1).toLowerCase() + string.substring(1);
    }
}
