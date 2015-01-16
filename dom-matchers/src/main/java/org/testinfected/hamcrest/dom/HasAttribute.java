package org.testinfected.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;

public class HasAttribute extends TypeSafeDiagnosingMatcher<Element> {
    private final String attributeName;

    public HasAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    protected boolean matchesSafely(Element actual, Description mismatchDescription) {
        boolean match = actual.hasAttribute(attributeName);
        if (!match) {
            mismatchDescription.appendText("no attribute ").appendValue(attributeName);
        }
        return match;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an element having attribute ").appendValue(attributeName);
    }

    public static Matcher<Element> isSelected() {
        return hasAttribute("selected");
    }

    public static Matcher<Element> isReadOnly() {
        return hasAttribute("readonly");
    }

    public static Matcher<Element> isChecked() {
        return hasAttribute("checked");
    }

    public static Matcher<Element> isDisabled() {
        return hasAttribute("disabled");
    }

    public static Matcher<Element> hasAttribute(String name) {
        return new HasAttribute(name);
    }
}