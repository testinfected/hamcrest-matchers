package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasAttributeValue.hasClassName;
import static org.testinfected.hamcrest.dom.HasSelector.hasSelector;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSelector("#content");
    }

    @Test
    public void
    matchesWhenAtLeastOneChildIsSelected() {
        assertMatches("does not match single subject", hasSelector("#content"), toElement("<html><body><div id='content'>content</div></body></html>"));
        assertMatches("does not match multiple subjects", hasSelector("li"), toElement("<html><body><ol><li>first</li><li>second</li></ol></body></html>"));
        assertDoesNotMatch("matches a different subject", hasSelector("#content"), toElement("<html><body><div>content</div></body></html>"));
    }

    @Test
    public void
    matchesSelectedChildrenInAnyOrder() {
        assertMatches("does not match child", hasSelector("#content", hasTag("div")), toElement("<html><body><div id='content'>content</div></body></html>"));
        assertMatches("does not match some children", hasSelector("ol > li", hasClassName("odd")), toElement("<html><body><ol><li class='odd'>first</li><li class='even'>second</li></ol></body></html>"));
        assertMatches("does not match all children", hasSelector("ol > li", hasClassName("even"), hasClassName("odd")), toElement("<html><body><ol><li class='odd'>first</li><li class='even'>second</li></ol></body></html>"));
        assertDoesNotMatch("matches different element", hasSelector("#content", hasTag("div")), toElement("<html><body><span id='content'>content</span></body></html>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has selector \"#content\"", hasSelector("#content"));
        assertDescription("has selector \"#content\" (a collection containing has tag \"div\")", hasSelector("#content", HasTag.hasTag(equalTo("div"))));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("no selector \"ul li\"", hasSelector("ul li"), toElement("<html><body><ol><li>first</li><li>second</li></ol></body></html>"));
        assertMismatchDescription("#content a collection containing has tag \"div\" tag was \"span\"", hasSelector("#content", HasTag.hasTag(equalTo("div"))), toElement("<html><body><span id='content'>content</span></body></html>"));
    }
}