package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.testinfected.hamcrest.dom.HasNoSelector.hasNoSelector;
import static com.threelevers.css.DocumentBuilder.dom;

public class HasNoSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasNoSelector("#unknown");
    }
    
    public void testMatchesWhenElementHasNoChildMatchingSelector() {
        assertMatches("not found", hasNoSelector("#unknown"), dom("<div id='content'>content</div>"));
        assertDoesNotMatch("found", hasNoSelector("#content"), dom("<div id='content'>content</div>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("matched element \"DIV\"", hasNoSelector("#content"), dom("<div id='content'></div>"));
    }
}
