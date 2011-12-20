package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasNoSelector.hasNoSelector;

public class HasNoSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasNoSelector("#unknown");
    }

    public void testMatchesWhenElementHasNoChildMatchingSelector() {
        assertMatches("not found", hasNoSelector("#unknown"), toElement("<div id='content'>content</div>"));
        assertDoesNotMatch("found", hasNoSelector("#content"), toElement("<div id='content'>content</div>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("matched element \"div\"", hasNoSelector("#content"), toElement("<div id='content'></div>"));
    }
}
