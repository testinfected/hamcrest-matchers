package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.equalToIgnoringCase;

public class WithTag extends FeatureMatcher<Element, String> {

    public WithTag(Matcher<? super String> valueMatcher) {
        super(valueMatcher, "element with tag", "element tag");
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getTagName();
    }

    public static Matcher<Element> withTag(Matcher<? super String> valueMatcher) {
        return new WithTag(valueMatcher);
    }

    @Factory
    public static Matcher<Element> withTag(String tagName) {
        return withTag(equalToIgnoringCase(tagName));
    }
}
