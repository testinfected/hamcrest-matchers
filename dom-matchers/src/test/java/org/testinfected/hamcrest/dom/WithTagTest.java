package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class WithTagTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withTag("div");
    }

    @Test public void
    matchesWhenElementHasMatchingTagName() {
        assertMatches("correct tag", withTag(equalToIgnoringCase("div")), a("div"));
        assertDoesNotMatch("incorrect tag", withTag(equalTo("div")), a("span"));
    }

    @Test public void
    providesConvenientShortcutForMatchingTagNameIgnoringCase() {
        assertMatches("same lowercase tag", withTag("div"), a("div"));
        assertMatches("same uppercase tag", withTag("DIV"), a("DIV"));
        assertMatches("upper case tag", withTag("DIV"), a("div"));
        assertMatches("lowercase correct tag", withTag("div"), a("DIV"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("element with tag \"div\"", withTag(equalTo("div")));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("element tag was \"span\"", withTag(equalTo("div")), a("span"));
    }

    private Element a(String tag) {
        return toElement(String.format("<%s></%s>", tag, tag));
    }
}
