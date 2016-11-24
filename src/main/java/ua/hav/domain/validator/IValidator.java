package ua.hav.domain.validator;

import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public interface IValidator<T> {
    Map<String, String> validate(T el, Class clazz);
}
