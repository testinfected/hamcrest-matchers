package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasAttribute.*;

public class HasAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasAttribute("selected");
    }

    @Test public void
    matchesDependingOnAttributePresence() {
        assertMatches("attribute not found", hasAttribute("selected"), elementWithAttribute("selected"));
        assertDoesNotMatch("attribute found", hasAttribute("selected"), elementWithAttribute("readonly"));
    }

    @Test public void
    providesShortcutForMatchingSelectedElements() {
        assertMatches("not selected", isSelected(), elementWithAttribute("selected"));
        assertDoesNotMatch("selected", isSelected(), elementWithAttribute("readonly"));
    }

    @Test public void
    providesShortcutForMatchingReadonlyElements() {
        assertMatches("not readonly", isReadOnly(), elementWithAttribute("readonly"));
        assertDoesNotMatch("readonly", isReadOnly(), elementWithAttribute("checked"));
    }

    @Test public void
    providesShortcutForMatchingCheckedElements() {
        assertMatches("not checked", isChecked(), elementWithAttribute("checked"));
        assertDoesNotMatch("checked", isChecked(), elementWithAttribute("disabled"));
    }

    @Test public void
    providesShortcutForMatchingDisabledElements() {
        assertMatches("not disabled", isDisabled(), elementWithAttribute("disabled"));
        assertDoesNotMatch("checked", isDisabled(), elementWithAttribute("selected"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("an element having attribute \"name\"", hasAttribute("name"));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("no attribute \"name\"", hasAttribute("name"), elementWithAttribute("other"));
    }

    private Element elementWithAttribute(String name) {
        return toElement(String.format("<input %s=\"\"/>", name));
    }
}