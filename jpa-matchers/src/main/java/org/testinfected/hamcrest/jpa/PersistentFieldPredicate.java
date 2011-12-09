package org.testinfected.hamcrest.jpa;

import com.google.common.base.Predicate;

import java.lang.reflect.Field;

public class PersistentFieldPredicate implements Predicate<Field> {

    public boolean apply(Field input) {
        return isPersistent(input);
    }

    private static boolean isPersistent(Field each) {
        return !Reflection.isStatic(each) && !Reflection.isTransient(each);
    }

    public static Field[] persistentFieldsOf(Object entity) {
        return Reflection.fieldsOf(entity, new PersistentFieldPredicate());
    }
}
