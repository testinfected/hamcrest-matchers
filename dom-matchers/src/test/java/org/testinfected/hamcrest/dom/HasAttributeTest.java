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
    matchesWhenAttributeValueMatches() {
        assertMatches("does not match attribute", hasAttribute("name", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("matches attribute when value differs", hasAttribute("name", equalTo("commit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("matches missing attribute", hasAttribute("value", equalTo("submit")), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAttributeValueUsingEqual() {
        assertMatches("does not match attribute", hasAttribute("name", "submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("matches a different value", hasAttribute("name", "Submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("matches incorrect attribute", hasAttribute("name", "commit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("matches missing attribute", hasAttribute("value", "submit"), anElementWithAttribute("name", "submit"));
    }

    @Test public void
    providesConvenientShortcutForMatchingId() {
        assertMatches("does not match id", hasId("content"), anElementWithAttribute("id", "content"));
        assertDoesNotMatch("matches a different id", hasId("content"), anElementWithAttribute("id", "header"));
    }

    @Test public void
    providesConvenientShortcutForMatchingName() {
        assertMatches("does not match name", hasName("fieldName"), anElementWithAttribute("name", "fieldName"));
        assertDoesNotMatch("matches a different name", hasName("fieldName"), anElementWithAttribute("name", "incorrectName"));
    }

    @Test public void
    providesConvenientShortcutForMatchingAClassName() {
        assertMatches("does not match class", hasClassName("text"), anElementWithAttribute("class", "text"));
        assertDoesNotMatch("matches another class", hasClassName("text"), anElementWithAttribute("class", "number"));
        assertMatches("does not match first class", hasClassName("text"), anElementWithAttribute("class", "text strong"));
        assertMatches("does not match last class", hasClassName("text"), anElementWithAttribute("class", "strong text"));
        assertMatches("does not match center class", hasClassName("text"), anElementWithAttribute("class", "bold text strong"));
        assertDoesNotMatch("matches look-alike class", hasClassName("text"), anElementWithAttribute("class", "textlongtext"));
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