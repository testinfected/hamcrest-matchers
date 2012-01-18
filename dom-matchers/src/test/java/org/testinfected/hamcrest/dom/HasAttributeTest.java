package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasAttribute.hasAttribute;
import static org.testinfected.hamcrest.dom.HasAttribute.hasClassName;
import static org.testinfected.hamcrest.dom.HasAttribute.hasId;
import static org.testinfected.hamcrest.dom.HasAttribute.hasName;

public class HasAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return HasAttribute.hasAttribute("name", equalTo("Submit"));
    }

    @Test public void
    matchesWhenElementHasAttributeMatchingValue() {
        assertMatches("correct attribute", HasAttribute.hasAttribute("name", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", HasAttribute.hasAttribute("name", equalTo("commit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", HasAttribute.hasAttribute("value", equalTo("submit")), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAttributeValueUsingEqual() {
        assertMatches("correct attribute", hasAttribute("name", "submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("correct attribute with incorrect case", hasAttribute("name", "Submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", hasAttribute("name", "commit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", hasAttribute("value", "submit"), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingId() {
        assertMatches("correct id", hasId("content"), anElementWithAttribute("id", "content"));
        assertDoesNotMatch("incorrect id", hasId("content"), anElementWithAttribute("id", "header"));
    }

    @Test public void
    providesConvenientShortcutForMatchingName() {
        assertMatches("correct name", hasName("fieldName"), anElementWithAttribute("name", "fieldName"));
        assertDoesNotMatch("incorrect name", hasName("fieldName"), anElementWithAttribute("name", "incorrectName"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAClassName() {
        assertMatches("correct class", hasClassName("text"), anElementWithAttribute("class", "text"));
        assertDoesNotMatch("incorrect class", hasClassName("text"), anElementWithAttribute("class", "number"));
        assertMatches("starting class", hasClassName("text"), anElementWithAttribute("class", "text strong"));
        assertMatches("ending class", hasClassName("text"), anElementWithAttribute("class", "strong text"));
        assertMatches("middle class", hasClassName("text"), anElementWithAttribute("class", "bold text strong"));
        assertDoesNotMatch("look-alike class", hasClassName("text"), anElementWithAttribute("class", "textlongtext"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("element with attribute \"name\" \"submit\"", hasAttribute("name", "submit"));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("element attribute \"name\" was \"Commit\"", hasAttribute("name", "submit"), anElementWithAttribute("name", "Commit"));
        assertMismatchDescription("element attribute \"name\" was \"\"", hasAttribute("name", "submit"), anElementWithAttribute("value", "submit"));
    }

    private Element anElementWithAttribute(String attributeName, String attributeValue) {
        return toElement(String.format("<div %s=\"%s\"></div>", attributeName, attributeValue));
    }
}