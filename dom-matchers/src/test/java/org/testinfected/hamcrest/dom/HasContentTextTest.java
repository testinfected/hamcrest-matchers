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
    matchesWhenElementHasMatchingTagName() {
        assertMatches("correct content", HasContentText.hasContent(equalTo("text")), anElementWithText("text"));
        assertDoesNotMatch("incorrect content", HasContentText.hasContent(equalTo("text")), anElementWithText("other text"));
    }

    @Test public void
    providesConvenientShortcutForMatchingContentTextUsingEqual() {
        assertMatches("same text", hasContent("text"), anElementWithText("text"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("an element with content text \"expected\"", HasContentText.hasContent(equalTo("expected")));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("element content text was \"different\"", HasContentText.hasContent(equalTo("expected")), anElementWithText("different"));
    }

    private Element anElementWithText(String content) {
        return toElement(String.format("<div>%s</div>", content));
    }
}