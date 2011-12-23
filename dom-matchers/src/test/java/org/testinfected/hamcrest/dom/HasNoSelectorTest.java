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

    @Test public void
    matchesWhenChildMatchesSelector() {
        assertMatches("not found", hasNoSelector("#unknown"), toElement("<div id='content'>content</div>"));
        assertDoesNotMatch("found", hasNoSelector("#content"), toElement("<div id='content'>content</div>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("matched element \"div\"", hasNoSelector("#content"), toElement("<div id='content'></div>"));
    }
}
