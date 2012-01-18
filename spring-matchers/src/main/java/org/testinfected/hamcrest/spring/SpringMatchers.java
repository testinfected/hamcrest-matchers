package org.testinfected.hamcrest.spring;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.ui.Model;

public class SpringMatchers {

    private SpringMatchers() {}

    /**
     * Checks that a web {@link org.springframework.ui.Model} contains a given
     * key-value pair.
     *
     * @param key the key under which the value to check is stored in the model.
     * @param value the expected value stored in the model.
     */
    public static <T> Matcher<Model> hasAttribute(final String key, T value) {
        return HasAttribute.hasAttribute(key, value);
    }

    /**
     * Checks that a web {@link org.springframework.ui.Model} contains a given
     * key-value pair. The key value must match the given matcher.
     *
     * @param key the key under which the value to check is stored in the model.
     * @param valueMatcher matcher used to check the value stored in the model.
     */
    public static <T> Matcher<Model> hasAttributeValue(final String key, Matcher<? super T> valueMatcher) {
        return HasAttribute.hasAttributeValue(key, valueMatcher);
    }

    /**
     * Checks that a web {@link org.springframework.ui.Model} contains some
     * non-null value stored under a specific key.
     *
     * @param key the key under which a non-null value should be stored in the model.
     */
    public static Matcher<Model> containsAttribute(final String key) {
        return HasAttribute.containsAttribute(key);
    }

    /**
     * Checks that a view indicates an HTTP redirection to a given location. 
     * @param location expected location of the redirection. 
     */
    public static Matcher<String> isRedirectedTo(String location) {
        return Matchers.equalTo("redirect:" + location);
    }
}
