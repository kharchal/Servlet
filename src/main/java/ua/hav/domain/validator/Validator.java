package ua.hav.domain.validator;

import ua.hav.domain.annotations.Reference;
import ua.hav.ui.tags.TagUtils;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public class Validator<T> implements IValidator<T> {
    private Map<String, String> errors;

    @Override
    public Map<String, String> validate(T el, Class clazz) {
        errors = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            String name = f.getName();
            try {
                if (!f.getName().equals("id")) {
                    String type = f.getType().getSimpleName();
//                    System.out.println("name = " + name);
                    if (f.isAnnotationPresent(Reference.class)) {
//
                        int id = (int) TagUtils.
                                getFieldValueByNameFromObjectField(f, el, "id");
                        if (id == 1) {
                            put(name);
                        }



                    }
                    if (f.isAnnotationPresent(Pattern.class)) {
                        String regexp = f.getAnnotation(Pattern.class).regexp();
//                        System.out.println("regexp = " + regexp);
                        if (type.equals("String")) {
                            String value = (String) f.get(el);
//                            System.out.println("value = " + value);
                            if (!value.matches(regexp)) {
                                put(name);
//                                System.out.println("errors = " + errors);
                            }
                        }
                    }
                    if (f.isAnnotationPresent(Size.class)) {
                        int min = f.getAnnotation(Size.class).min();
                        int max = f.getAnnotation(Size.class).max();
//                        System.out.println("max = " + max);
//                        System.out.println("min = " + min);
                        if (type.equals("String")) {
                            int value = ((String) f.get(el)).length();
//                            System.out.println("value = " + value);
                            if (value < min || value > max) {
                                put(name);
//                                System.out.println("errors = " + errors);
                            }
                        }
                        if (type.equals("int")) {
                            int value = (int) f.get(el);
//                            System.out.println("value = " + value);
                            if (value < min || value > max) {
                                put(name);
//                                System.out.println("errors = " + errors);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }

    private void put(String name) {
        errors.put(name, "wrong " + name);
    }
}
