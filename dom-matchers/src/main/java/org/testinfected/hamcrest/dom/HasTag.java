package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.equalToIgnoringCase;

public class HasTag extends FeatureMatcher<Element, String> {

    public HasTag(Matcher<? super String> valueMatcher) {
        super(valueMatcher, "has tag", "tag");
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getTagName();
    }

    @Factory
    public static Matcher<Element> hasTag(String tagName) {
        return hasTag(equalToIgnoringCase(tagName));
    }

    public static Matcher<Element> hasTag(Matcher<? super String> valueMatcher) {
        return new HasTag(valueMatcher);
    }
}
