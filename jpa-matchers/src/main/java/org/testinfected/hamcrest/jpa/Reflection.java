package org.testinfected.hamcrest.jpa;

import com.google.common.base.Predicate;
import org.testinfected.hamcrest.ExceptionImposter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    private Reflection() {}

    public static Object readField(Object argument, Field field) {
        try {
            boolean accessible = byPassSecurity(field);
            Object value = field.get(argument);
            restoreSecurity(field, accessible);
            return value;
        } catch (IllegalAccessException e) {
            throw ExceptionImposter.imposterize(e);
        }
    }

    public static boolean isTransient(Field each) {
        return Modifier.isTransient(each.getModifiers());
    }

    public static boolean isStatic(Field each) {
        return Modifier.isStatic(each.getModifiers());
    }

    public static Field[] fieldsOf(Object entity, Predicate<Field> predicate) {
        return fieldsOf(entity.getClass(), predicate);
    }

    public static Field[] fieldsOf(Class<?> entity, Predicate<Field> predicate) {
        Field[] allFields = entity.getDeclaredFields();
        List<Field> retained = new ArrayList<Field>();
        for (Field each : allFields) {
            if (predicate.apply(each)) retained.add(each);
        }
        return retained.toArray(new Field[retained.size()]);
    }

    private static void restoreSecurity(Field field, boolean accessible) {
        field.setAccessible(accessible);
    }

    private static boolean byPassSecurity(Field field) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        return accessible;
    }
}
