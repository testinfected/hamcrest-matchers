package org.testinfected.hamcrest.dom;

import com.threelevers.css.Selector;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.testinfected.hamcrest.dom.WithAttribute.*;
import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.core.IsEqual.equalTo;

public class WithAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withAttribute("name", equalTo("Submit"));
    }

    public void testMatchesWhenElementHasAttributeMatchingValue() {
        assertMatches("correct attribute", withAttribute("name", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertMatches("correct attribute with different case", withAttribute("NAME", equalTo("submit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", equalTo("commit")), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", equalTo("submit")), anElementWithAttribute("name", "submit"));
    }

    public void testProvidesConvenientShortcutForMatchingIdenticalValues() {
        assertMatches("correct attribute", withAttribute("name", "submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("correct attribute with incorrect case", withAttribute("name", "Submit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("incorrect attribute", withAttribute("name", "commit"), anElementWithAttribute("name", "submit"));
        assertDoesNotMatch("missing attribute", withAttribute("value", "submit"), anElementWithAttribute("name", "submit"));
    }

    public void testProvidesConvenientShortcutForMatchingId() {
        assertMatches("correct id", withId("content"), anElementWithAttribute("id", "content"));
        assertDoesNotMatch("incorrect id", withId("content"), anElementWithAttribute("id", "header"));
    }

    public void testProvidesConvenientShortcutForMatchingName() {
        assertMatches("correct name", withName("fieldName"), anElementWithAttribute("name", "fieldName"));
        assertDoesNotMatch("incorrect name", withName("fieldName"), anElementWithAttribute("name", "incorrectName"));
    }

    public void testProvidesConvenientShortcutForMatchingAClassName() {
        assertMatches("correct class", withClassName("text"), anElementWithAttribute("class", "text"));
        assertDoesNotMatch("incorrect class", withClassName("text"), anElementWithAttribute("class", "number"));
        assertMatches("starting class", withClassName("text"), anElementWithAttribute("class", "text strong"));
        assertMatches("ending class", withClassName("text"), anElementWithAttribute("class", "strong text"));
        assertMatches("middle class", withClassName("text"), anElementWithAttribute("class", "bold text strong"));
        assertDoesNotMatch("look-alike class", withClassName("text"), anElementWithAttribute("class", "textlongtext"));
    }

    public void testHasAReadableDescription() {
        assertDescription("element with attribute \"name\" \"submit\"", withAttribute("name", "submit"));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element attribute \"name\" was \"Commit\"", withAttribute("name", "submit"), anElementWithAttribute("name", "Commit"));
        assertMismatchDescription("element attribute \"name\" was \"\"", withAttribute("name", "submit"), anElementWithAttribute("value", "submit"));
    }

    private Element anElementWithAttribute(String attributeName, String attributeValue) {
        return element(String.format("<div %s=\"%s\"></div>", attributeName, attributeValue));
    }

    private Element element(String html) {
        return Selector.from(dom(html)).selectUnique("html > body > *");
    }

}