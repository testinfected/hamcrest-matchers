package org.testinfected.hamcrest.dom;

import com.google.common.collect.Iterables;
import org.hamcrest.*;
import org.w3c.dom.Element;

import static com.threelevers.css.Selector.from;

public class HasSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<Iterable<Element>> elementsMatcher;

    public HasSelector(String selector, Matcher<Iterable<Element>> elementsMatcher) {
        this.selector = selector;
        this.elementsMatcher = elementsMatcher;
    }

    @Override
    protected boolean matchesSafely(Element element, Description mismatchDescription) {
        Iterable<Element> elements = from(element).select(selector);
        if (Iterables.isEmpty(elements)) {
            mismatchDescription.appendText("no selector ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        boolean valueMatches = elementsMatcher.matches(elements);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            elementsMatcher.describeMismatch(elements, mismatchDescription);
        }
        return valueMatches;
    }

    public void describeTo(Description description) {
        description.appendText("has selector \"");
        description.appendText(selector);
        description.appendText("\" ");
        elementsMatcher.describeTo(description);
    }

    @Factory
    public static Matcher<Element> hasSelector(String selector, Matcher<Iterable<Element>> elementsMatcher) {
        return new HasSelector(selector, elementsMatcher);
    }

    @Factory
    public static Matcher<Element> hasSelector(String selector, Matcher<? super Element>... elementsMatchers) {
        return hasSelector(selector, Matchers.<Element>hasItems(elementsMatchers));
    }

}

