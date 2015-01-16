package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AnyOf.anyOf;

public class HasAttributeValue extends FeatureMatcher<Element, String> {
    private final String attributeName;

    public HasAttributeValue(String attributeName, Matcher<? super String> valueMatcher) {
        super(valueMatcher, "element with attribute \"" + attributeName + "\"", "element attribute \"" + attributeName + "\"");
        this.attributeName = attributeName;
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getAttribute(attributeName);
    }

    @Factory
    public static Matcher<Element> hasAttribute(String name, String value) {
        return hasAttribute(name, equalTo(value));
    }

    @Factory
    public static Matcher<Element> hasId(String id) {
        return hasAttribute("id", equalTo(id));
    }

    @Factory
    public static Matcher<Element> hasName(String name) {
        return hasAttribute("name", equalTo(name));
    }

    @SuppressWarnings("unchecked")
    @Factory
    public static Matcher<Element> hasClassName(String className) {
        return hasAttribute("class", anyOf(
                equalTo(className),
                startsWith(className + " "),
                endsWith(" " + className),
                containsString(" " + className + " ")
        ));
    }

    @Factory
    public static Matcher<Element> hasAttribute(String name, Matcher<? super String> valueMatcher) {
        return new HasAttributeValue(name, valueMatcher);
    }
}
