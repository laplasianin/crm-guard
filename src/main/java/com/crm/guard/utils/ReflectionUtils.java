package com.crm.guard.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Object getFieldByName(Object object, String entityField) throws NoSuchFieldException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(entityField);

        field.setAccessible(true);

        return field.get(object);
    }

}
