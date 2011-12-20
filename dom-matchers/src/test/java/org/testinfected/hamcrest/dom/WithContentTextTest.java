package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.WithContentText.withContent;

public class WithContentTextTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withContent("text");
    }

    public void testMatchesWhenElementHasMatchingTagName() {
        assertMatches("correct content", withContent(equalTo("text")), anElementWithText("text"));
        assertDoesNotMatch("incorrect content", withContent(equalTo("text")), anElementWithText("other text"));
    }

    public void testProvidesConvenientShortcutForMatchingIdenticalText() {
        assertMatches("same text", withContent("text"), anElementWithText("text"));
    }

    public void testHasAReadableDescription() {
        assertDescription("an element with content text \"expected\"", withContent(equalTo("expected")));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element content text was \"different\"", withContent(equalTo("expected")), anElementWithText("different"));
    }

    private Element anElementWithText(String content) {
        return toElement(String.format("<div>%s</div>", content));
    }
}