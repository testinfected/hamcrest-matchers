package org.testinfected.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;

import static com.threelevers.css.Selector.from;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.anything;
import static org.testinfected.hamcrest.dom.DomMatchers.anElement;

public class HasUniqueSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<? super Element> subjectMatcher;

    public HasUniqueSelector(String selector, Matcher<? super Element> subjectMatcher) {
        this.selector = selector;
        this.subjectMatcher = subjectMatcher;
    }

    @Override
    protected boolean matchesSafely(Element doc, Description mismatchDescription) {
        Iterable<Element> allElements = from(doc).select(selector);
        if (!isSingleton(allElements)) {
            mismatchDescription.appendText(valueOf(IterablesUtils.size(allElements)));
            mismatchDescription.appendText(" selector(s) ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        Element element = IterablesUtils.getOnlyElement(allElements);
        boolean valueMatches = subjectMatcher.matches(element);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            subjectMatcher.describeMismatch(element, mismatchDescription);
        }
        return valueMatches;
    }

    private boolean isSingleton(Iterable<Element> elements) {
        return IterablesUtils.size(elements) == 1;
    }

    public void describeTo(Description description) {
        description.appendText("has unique selector \"");
        description.appendText(selector);
        description.appendText("\" ");
        subjectMatcher.describeTo(description);
    }

    @Factory
    public static Matcher<Element> hasUniqueSelector(String selector) {
        return new HasUniqueSelector(selector, anything());
    }

    @Factory
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element>... subjectMatchers) {
        return hasUniqueSelector(selector, anElement(subjectMatchers));
    }

    @Factory
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element> subjectMatcher) {
        return new HasUniqueSelector(selector, subjectMatcher);
    }
}