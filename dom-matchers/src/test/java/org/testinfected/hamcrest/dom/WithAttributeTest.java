package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.WithAttribute.withAttribute;
import static org.testinfected.hamcrest.dom.WithAttribute.withClassName;
import static org.testinfected.hamcrest.dom.WithAttribute.withId;
import static org.testinfected.hamcrest.dom.WithAttribute.withName;

public class WithAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withAttribute("name", equalTo("Submit"));
    }

    @Test public void
    matchesWhenElementHasAttributeMatchingValue() {
        assertMatches("correct attribute", withAttribute("name", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", equalTo("commit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", equalTo("submit")), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAttributeValueUsingEqual() {
        assertMatches("correct attribute", withAttribute("name", "submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("correct attribute with incorrect case", withAttribute("name", "Submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", "commit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", "submit"), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingId() {
        assertMatches("correct id", withId("content"), anElementWithAttribute("id", "content"));
        assertDoesNotMatch("incorrect id", withId("content"), anElementWithAttribute("id", "header"));
    }

    @Test public void
    providesConvenientShortcutForMatchingName() {
        assertMatches("correct name", withName("fieldName"), anElementWithAttribute("name", "fieldName"));
        assertDoesNotMatch("incorrect name", withName("fieldName"), anElementWithAttribute("name", "incorrectName"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAClassName() {
        assertMatches("correct class", withClassName("text"), anElementWithAttribute("class", "text"));
        assertDoesNotMatch("incorrect class", withClassName("text"), anElementWithAttribute("class", "number"));
        assertMatches("starting class", withClassName("text"), anElementWithAttribute("class", "text strong"));
        assertMatches("ending class", withClassName("text"), anElementWithAttribute("class", "strong text"));
        assertMatches("middle class", withClassName("text"), anElementWithAttribute("class", "bold text strong"));
        assertDoesNotMatch("look-alike class", withClassName("text"), anElementWithAttribute("class", "textlongtext"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("element with attribute \"name\" \"submit\"", withAttribute("name", "submit"));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("element attribute \"name\" was \"Commit\"", withAttribute("name", "submit"), anElementWithAttribute("name", "Commit"));
        assertMismatchDescription("element attribute \"name\" was \"\"", withAttribute("name", "submit"), anElementWithAttribute("value", "submit"));
    }

    private Element anElementWithAttribute(String attributeName, String attributeValue) {
        return toElement(String.format("<div %s=\"%s\"></div>", attributeName, attributeValue));
    }
}