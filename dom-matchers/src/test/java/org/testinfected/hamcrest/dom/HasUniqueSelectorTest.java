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
        assertMatches("does mot match single subject", hasUniqueSelector("#content"), toElement("<div id='content'>content</div>"));
        assertDoesNotMatch("matches a different subject", hasUniqueSelector("#content"), toElement("<div>content</div>"));
        assertDoesNotMatch("matches subject several times", hasUniqueSelector("li"), toElement("<ol><li>first</li><li>second</li></ol>"));
    }

    @Test public void
    matchesWhenSelectedChildMatches() {
        assertMatches("does not match child", hasUniqueSelector("#content", hasTag("div")), toElement("<div id='content'>content</div>"));
        assertDoesNotMatch("matches a different child", hasUniqueSelector("#content", hasTag("div")), toElement("<span id='content'>content</span>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has unique selector \"#content\" has tag \"div\"", hasUniqueSelector("#content", HasTag.hasTag(equalTo("div"))));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("2 selector(s) \"li\"", hasUniqueSelector("li"), toElement("<ol><li>first</li><li>second</li></ol>"));
    }
}
