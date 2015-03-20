package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasUniqueSelector.hasUniqueSelector;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasUniqueSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasUniqueSelector("#content");
    }

    @Test public void
    matchesWhenASingleChildIsSelected() {
        assertMatches("does mot match single subject", hasUniqueSelector("#content"), toElement("<html><body><div id='content'>content</div></body></html>"));
        assertDoesNotMatch("matches a different subject", hasUniqueSelector("#content"), toElement("<html><body><div>content</div></body></html>"));
        assertDoesNotMatch("matches subject several times", hasUniqueSelector("li"), toElement("<html><body><ol><li>first</li><li>second</li></ol></body></html>"));
    }

    @Test public void
    matchesWhenSelectedChildMatches() {
        assertMatches("does not match child", hasUniqueSelector("#content", hasTag("div")), toElement("<html><body><div id='content'>content</div></body></html>"));
        assertDoesNotMatch("matches a different child", hasUniqueSelector("#content", hasTag("div")), toElement("<html><body><span id='content'>content</span></body></html>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has unique selector \"#content\" has tag \"div\"", hasUniqueSelector("#content", HasTag.hasTag(equalTo("div"))));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("2 selector(s) \"li\"", hasUniqueSelector("li"), toElement("<html><body><ol><li>first</li><li>second</li></ol></body></html>"));
    }
}
