package org.testinfected.hamcrest.jpa;

import org.hamcrest.*;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.IsEqual;

import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import static org.testinfected.hamcrest.jpa.IsComponentEqual.componentEqualTo;
import static org.testinfected.hamcrest.jpa.PersistentFieldPredicate.persistentFieldsOf;


public class SamePersistentFieldsAs<T> extends TypeSafeDiagnosingMatcher<T> {

    private final T expectedEntity;

    public SamePersistentFieldsAs(T expectedEntity) {
        this.expectedEntity = expectedEntity;
    }

    @Override
    protected boolean matchesSafely(T argument, Description mismatchDescription) {
        return isCompatibleType(argument, mismatchDescription)
                && hasMatchingPersistentFields(argument, mismatchDescription);
    }

    private boolean isCompatibleType(T argument, Description mismatchDescription) {
        if (!expectedEntity.getClass().isAssignableFrom(argument.getClass())) {
            mismatchDescription.appendText("is incompatible type: " + argument.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    private boolean hasMatchingPersistentFields(T argument, Description mismatchDescription) {
        for (Matcher<? extends T> matcher : persistentFieldsMatchersFor(expectedEntity)) {
            if (!matcher.matches(argument)) {
                matcher.describeMismatch(argument, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    private static <T> Iterable<Matcher<? extends T>> persistentFieldsMatchersFor(T entity) {
        Collection<Matcher<? extends T>> matchers = new ArrayList<Matcher<? extends T>>();
        for (Field field : persistentFieldsOf(entity)) {
            matchers.add(new FieldMatcher<T>(field, valueMatcherFor(entity, field)));
        }
        return matchers;
    }

    private static Matcher<?> valueMatcherFor(Object entity, Field field) {
        if (isEmbedded(field)) return componentEqualTo(Reflection.readField(entity, field));
        if (isAssociation(field)) return anyAssociation();
        return equalTo(Reflection.readField(entity, field));
    }

    private static Matcher<?> anyAssociation() {
        return DescribedAs.describedAs("an association", Matchers.anything());
    }

    private static <T> IsEqual<T> equalTo(final T arg) {
        return new IsEqual<T>(arg);
    }

    private static boolean isAssociation(Field field) {
        return isManyToOne(field) || isOneToMany(field) || isManyToMany(field);
    }

    private static boolean isManyToMany(Field field) {
        return field.getAnnotation(ManyToMany.class) != null;
    }

    private static boolean isOneToMany(Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    private static boolean isManyToOne(Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    private static boolean isEmbedded(Field field) {
        return isEmbeddable(field.getType());
    }

    private static boolean isEmbeddable(final Class<?> type) {
        return type.getAnnotation(Embeddable.class) != null;
    }

    public void describeTo(Description description) {
        description.appendText("with fields [");
        boolean addSeparator = false;
        for (Field field : persistentFieldsOf(expectedEntity)) {
            if (addSeparator) description.appendText(", ");
            description.appendText(field.getName() + ": ");
            valueMatcherFor(expectedEntity, field).describeTo(description);
            addSeparator = true;
        }
        description.appendText("]");
    }

    public static class FieldMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

        private final Field field;
        private final Matcher<?> valueMatcher;

        public FieldMatcher(Field field, Matcher<?> valueMatcher) {
            this.field = field;
            this.valueMatcher = valueMatcher;
        }

        protected boolean matchesSafely(T argument, Description mismatchDescription) {
            Object actualValue = Reflection.readField(argument, field);
            boolean valueMatches = valueMatcher.matches(actualValue);
            if (!valueMatches) {
                mismatchDescription.appendText(field.getName() + " ");
                valueMatcher.describeMismatch(actualValue, mismatchDescription);
            }
            return valueMatches;
        }

        public void describeTo(Description description) {
            description.appendText(field.getName() + ": ").appendDescriptionOf(valueMatcher);
        }
    }

    @Factory
    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return new SamePersistentFieldsAs<T>(entity);
    }
}