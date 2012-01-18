package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasTagTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasTag("div");
    }

    @Test public void
    matchesWhenElementHasMatchingTagName() {
        assertMatches("correct tag", HasTag.hasTag(equalToIgnoringCase("div")), a("div"));
        assertDoesNotMatch("incorrect tag", HasTag.hasTag(equalTo("div")), a("span"));
    }

    @Test public void
    providesConvenientShortcutForMatchingTagNameIgnoringCase() {
        assertMatches("same lowercase tag", hasTag("div"), a("div"));
        assertMatches("same uppercase tag", hasTag("DIV"), a("DIV"));
        assertMatches("upper case tag", hasTag("DIV"), a("div"));
        assertMatches("lowercase correct tag", hasTag("div"), a("DIV"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("element with tag \"div\"", HasTag.hasTag(equalTo("div")));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("element tag was \"span\"", HasTag.hasTag(equalTo("div")), a("span"));
    }

    private Element a(String tag) {
        return toElement(String.format("<%s></%s>", tag, tag));
    }
}
