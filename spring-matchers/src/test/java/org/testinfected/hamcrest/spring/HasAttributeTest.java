package org.testinfected.hamcrest.spring;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.spring.HasAttribute.containsAttribute;
import static org.testinfected.hamcrest.spring.HasAttribute.hasAttribute;
import static org.testinfected.hamcrest.spring.HasAttribute.hasAttributeValue;

public class HasAttributeTest extends AbstractMatcherTest {

    ExtendedModelMap shouldMatch = new ExtendedModelMap() {{
        addAttribute("key", "matches");
    }};

    ExtendedModelMap shouldNotMatch = new ExtendedModelMap() {{
        addAttribute("key", "does not match");
    }};

    ExtendedModelMap missingKey = new ExtendedModelMap() {{
        addAttribute("missing", "value");
    }};

    @Override protected Matcher<? super Model> createMatcher() {
        return hasAttributeValue("key", equalTo("matches"));
    }
    
    public void testMatchesValueUnderGivenKey() {
        assertMatches("correct value", createMatcher(), shouldMatch);
        assertDoesNotMatch("incorrect value", createMatcher(), shouldNotMatch);
        assertDoesNotMatch("missing key", createMatcher(), missingKey);
    }
    
    public void testProvidesConvenientShortcutForMatchingEqualValue() {
        assertMatches("equal value", hasAttribute("key", "matches"), shouldMatch);
    }
    
    public void testProvidesConvenientShortcutForCheckingKey() {
        assertMatches("key match", containsAttribute("key"), shouldMatch);
        assertDoesNotMatch("no key match", containsAttribute("key"), missingKey);
    }

    public void testHasHumanReadableDescription() {
        assertDescription("with \"key\" \"matches\"", createMatcher());
    }

    public void testHasHumanReadableMismatchDescription() {
        assertMismatchDescription("\"key\" was \"does not match\"", createMatcher(), shouldNotMatch);
    }
}
