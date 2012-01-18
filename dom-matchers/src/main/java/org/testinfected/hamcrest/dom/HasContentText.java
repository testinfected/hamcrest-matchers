package org.testinfected.hamcrest.dom;

import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.core.IsBlankString.isBlank;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

public class HasContentText extends FeatureMatcher<Element, String> {

    public HasContentText(Matcher<? super String> contentMatcher) {
        super(contentMatcher, "an element with content text", "element content text");
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getTextContent();
    }

    @Factory
    public static Matcher<Element> hasBlankContent() {
        return hasContent(isBlank());
    }

    @Factory
    public static Matcher<Element> hasContent(String contentText) {
        return hasContent(equalTo(contentText));
    }

    @Factory
    public static Matcher<Element> hasContent(Matcher<? super String> contentMatcher) {
        return new HasContentText(contentMatcher);
    }

}
