package org.testinfected.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;

import static com.google.common.collect.Iterables.isEmpty;
import static com.threelevers.css.Selector.from;

public class HasNoSelector extends TypeSafeDiagnosingMatcher<Element> {

    private String selector;

    public HasNoSelector(String selector) {
        this.selector = selector;
    }

    @Override
    protected boolean matchesSafely(Element doc, Description mismatchDescription) {
        Iterable<Element> selected = from(doc).select(selector);
        if (!isEmpty(selected)) {
            Element match = selected.iterator().next();
            mismatchDescription.appendText("found element \"" + match.getTagName() + "\"");
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("has no selector \"" + this.selector + "\"");
    }

    @Factory
    public static Matcher<Element> hasNoSelector(String selector) {
        return new HasNoSelector(selector);
    }
}
