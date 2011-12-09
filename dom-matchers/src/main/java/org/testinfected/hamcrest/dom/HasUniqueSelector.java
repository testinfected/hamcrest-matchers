package org.testinfected.hamcrest.dom;

import com.google.common.collect.Iterables;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;

import static com.threelevers.css.Selector.from;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.anything;

public class HasUniqueSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<? super Element> elementMatcher;

    public HasUniqueSelector(String selector, Matcher<? super Element> elementMatcher) {
        this.selector = selector;
        this.elementMatcher = elementMatcher;
    }

    @Override
    protected boolean matchesSafely(Element doc, Description mismatchDescription) {
        Iterable<Element> allElements = from(doc).select(selector);
        if (!isSingleton(allElements)) {
            mismatchDescription.appendText(valueOf(Iterables.size(allElements)));
            mismatchDescription.appendText(" selector(s) ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        Element element = Iterables.getOnlyElement(allElements);
        boolean valueMatches = elementMatcher.matches(element);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            elementMatcher.describeMismatch(element, mismatchDescription);
        }
        return valueMatches;
    }

    private boolean isSingleton(Iterable<Element> elements) {
        return Iterables.size(elements) == 1;
    }

    public void describeTo(Description description) {
        description.appendText("has unique selector \"");
        description.appendText(selector);
        description.appendText("\" ");
        elementMatcher.describeTo(description);
    }

    @Factory
    public static Matcher<Element> hasUniqueSelector(String selector) {
        return new HasUniqueSelector(selector, anything());
    }

    @Factory
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element> elementMatcher) {
        return new HasUniqueSelector(selector, elementMatcher);
    }
}