package org.testinfected.hamcrest.dom;

import com.threelevers.css.Elements;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.w3c.dom.Element;

public class HasChildren extends FeatureMatcher<Element, Iterable<Element>> {

    public HasChildren(Matcher<? super Iterable<Element>> childrenMatcher) {
        super(childrenMatcher, "an element with children", "element children");
    }

    protected Iterable<Element> featureValueOf(Element actual) {
        return Elements.children(actual);
    }

    @Factory
    public static Matcher<Element> hasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        return new HasChildren(childrenMatcher);
    }

    @Factory
    public static Matcher<Element> hasChildren(Matcher<Element>... childrenMatchers) {
        return hasChildren(Matchers.<Element>contains(childrenMatchers));
    }

    @SuppressWarnings("unchecked")
    @Factory
    public static Matcher<Element> hasChild(Matcher<Element> childMatcher) {
        return hasChildren(Matchers.<Element>hasItems(childMatcher));
    }
}
