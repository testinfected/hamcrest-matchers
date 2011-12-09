package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AnyOf.anyOf;

public class WithAttribute extends FeatureMatcher<Element, String> {
    private final String attributeName;

    public WithAttribute(String attributeName, Matcher<? super String> valueMatcher) {
        super(valueMatcher, "element with attribute \"" + attributeName + "\"", "element attribute \"" + attributeName + "\"");
        this.attributeName = attributeName;
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getAttribute(attributeName);
    }

    @Factory
    public static Matcher<Element> withAttribute(String name, Matcher<? super String> valueMatcher) {
        return new WithAttribute(name, valueMatcher);
    }

    @Factory
    public static Matcher<Element> withAttribute(String name, String value) {
        return withAttribute(name, equalTo(value));
    }

    @Factory
    public static Matcher<Element> withId(String id) {
        return withAttribute("id", equalTo(id));
    }

    @SuppressWarnings("unchecked")
    @Factory
    public static Matcher<Element> withClassName(String className) {
        return withAttribute("class", anyOf(
                equalTo(className),
                startsWith(className + " "),
                endsWith(" " + className),
                containsString(" " + className + " ")
        ));
    }

    @Factory
    public static Matcher<Element> withName(String name) {
        return withAttribute("name", equalTo(name));
    }
}
