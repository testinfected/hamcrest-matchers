package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasContentText.hasContent;

public class HasContentTextTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasContent("text");
    }

    @Test public void
    matchesWhenContentMatches() {
        assertMatches("does not match content", hasContent(equalTo("text")), anElementWithText("text"));
        assertDoesNotMatch("matches different content", hasContent(equalTo("text")), anElementWithText("other text"));
    }

    @Test public void
    providesConvenientShortcutForMatchingContentUsingEqual() {
        assertMatches("does not match same text", hasContent("text"), anElementWithText("text"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has content text \"expected\"", hasContent(equalTo("expected")));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("text was \"different\"", hasContent(equalTo("expected")), anElementWithText("different"));
    }

    private Element anElementWithText(String content) {
        return toElement(String.format("<div>%s</div>", content));
    }
}