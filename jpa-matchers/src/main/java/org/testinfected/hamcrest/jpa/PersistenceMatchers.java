package org.testinfected.hamcrest.jpa;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.anything;

/**
 * A collection of hamcrest matchers to validate
 * field values of persistent objects. 
 */
public class PersistenceMatchers {

    private PersistenceMatchers() {}

    /**
     * Checks that an entity has a field with a value that is matched by the provided matcher.
     *
     * The field does not have to be public or to have a visible accessor. Field is accessed directly.
     *
     * @param field the name of a field
     * @param value a matcher to validate the field value.
     */
    public static <T> Matcher<T> hasField(String field, Matcher<?> value) {
        return HasFieldWithValue.hasField(field, value);
    }

    /**
     * Checks that an entity has a given field, whatever its value.
     *
     * The field does not have to be public or to have a visible accessor. Field is accessed directly.
     *
     * @param field the name of the expected field
     */
    public static <T> Matcher<T> hasField(String field) {
        return HasFieldWithValue.hasField(field);
    }

    /**
     * Checks that a component (aka value object) is equal to another
     * given component.  A null component is considered to be equal to
     * a component with only null persistent field values. Persistent fields
     * of a component are fields that are neither static or transient.
     *
     * @param component the component to match against.
     */
    public static <T> Matcher<T> componentEqualTo(T component) {
        return IsComponentEqual.componentEqualTo(component);
    }

    /**
     * Checks that an entity has the same persistent field values
     * than another entity. Persistent fields of an entity are
     * fields that are neither static or transient.
     *
     * @param entity the entity to compare to. 
     */
    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return SamePersistentFieldsAs.samePersistentFieldsAs(entity);
    }
}
