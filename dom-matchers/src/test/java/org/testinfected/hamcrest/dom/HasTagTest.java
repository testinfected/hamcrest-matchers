package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;
import org.w3c.dom.Element;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasTagTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasTag("div");
    }

    @Test public void
    matchesWhenTagNameMatches() {
        assertMatches("does not match tag", hasTag(equalTo("div")), a("div"));
        assertDoesNotMatch("matches a different tag", hasTag(equalTo("div")), a("span"));
    }

    @Test public void
    providesConvenientShortcutForMatchingTagNameIgnoringCase() {
        assertMatches("does not match lowercase tag", hasTag("DIV"), a("div"));
        assertMatches("does not match uppercase tag", hasTag("div"), a("DIV"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has tag \"div\"", hasTag(equalTo("div")));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("tag was \"span\"", hasTag(equalTo("div")), a("span"));
    }

    private Element a(String tag) {
        return toElement(String.format("<%s></%s>", tag, tag));
    }
}
