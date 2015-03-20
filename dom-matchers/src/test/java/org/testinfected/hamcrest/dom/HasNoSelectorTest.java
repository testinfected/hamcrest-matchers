package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasNoSelector.hasNoSelector;

public class HasNoSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasNoSelector("#unknown");
    }

    @Test
    public void
    matchesWhenSelectorHasNoSubject() {
        assertMatches("does not match unselected", hasNoSelector("#unknown"), toElement("<html><body><div id='content'>content</div></body></html>"));
        assertDoesNotMatch("matches selected", hasNoSelector("#content"), toElement("<html><body><div id=\"content\">content</div></body></html>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("found element \"div\"", hasNoSelector("#content"), toElement("<html><body><div id='content'></div></body></html>"));
    }
}