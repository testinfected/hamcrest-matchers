package org.testinfected.hamcrest.spring;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.*;

public class HasAttribute<T> extends FeatureMatcher<Model, T> {
    private final String key;

    public HasAttribute(String key, Matcher<? super T> valueMatcher) {
        super(valueMatcher, "with \"" + key + "\"", "\"" + key + "\"");
        this.key = key;
    }

    @SuppressWarnings("unchecked")
    @Override protected T featureValueOf(Model actual) {
        return (T) actual.asMap().get(key);
    }

    @Factory
    public static <T> Matcher<Model> hasAttribute(String key, T value) {
        return hasAttributeValue(key, equalTo(value));
    }

    @Factory
    public static <T> Matcher<Model> hasAttributeValue(String key, Matcher<? super T> valueMatcher) {
        return new HasAttribute<T>(key, valueMatcher);
    }

    public static <T> Matcher<Model> containsAttribute(String key) {
        return new HasAttribute<T>(key, not(nullValue()));
    }
}
