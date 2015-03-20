package org.testinfected.hamcrest.dom;

import org.hamcrest.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Set;

public class HasSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<Iterable<Element>> subjectsMatcher;

    public HasSelector(String selector) {
        this(selector, null);
    }

    public HasSelector(String selector, Matcher<Iterable<Element>> subjectsMatchers) {
        this.selector = selector;
        this.subjectsMatcher = subjectsMatchers;
    }

    @Override
    protected boolean matchesSafely(Element element, Description mismatchDescription) {
        Set<Node> elements = Selector.from(element).selectAll(selector);
        if (elements.size() == 0) {
            mismatchDescription.appendText("no selector ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        if (subjectsMatcher == null) return true;

        boolean valueMatches = subjectsMatcher.matches(elements);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            subjectsMatcher.describeMismatch(elements, mismatchDescription);
        }
        return valueMatches;
    }

    public void describeTo(Description description) {
        description.appendText("has selector \"");
        description.appendText(selector);
        description.appendText("\"");
        if (subjectsMatcher != null) {
            description.appendText(" ");
            subjectsMatcher.describeTo(description);
        }
    }

    @Factory
    public static Matcher<Element> hasSelector(String selector) {
        return new HasSelector(selector);
    }

    @Factory
    public static Matcher<Element> hasSelector(String selector, Matcher<? super Element>... subjectsMatchers) {
        return hasSelector(selector, Matchers.<Element>hasItems(subjectsMatchers));
    }

    @Factory
    public static Matcher<Element> hasSelector(String selector, Matcher<Iterable<Element>> subjectsMatcher) {
        return new HasSelector(selector, subjectsMatcher);
    }

}

