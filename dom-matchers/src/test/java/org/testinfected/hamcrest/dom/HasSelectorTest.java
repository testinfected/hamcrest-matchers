package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.HasAttribute.hasClassName;
import static org.testinfected.hamcrest.dom.HasSelector.hasSelector;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSelector("#content");
    }

    @Test public void
    matchesWhenAtLeastOneChildMatchesSelector() {
        assertMatches("single element", hasSelector("#content"), toElement("<div id='content'>content</div>"));
        assertMatches("multiple elements", hasSelector("li"), toElement("<ol><li>first</li><li>second</li></ol>"));
        assertDoesNotMatch("element not found", hasSelector("#content"), toElement("<div>content</div>"));
    }

    @Test public void
    matchesSelectedChildrenAgainstSpecifiedMatchersInAnyOrder() {
        assertMatches("matching child", hasSelector("#content", hasTag("div")), toElement("<div id='content'>content</div>"));
        assertMatches("matching some children", hasSelector("ol > li", hasClassName("odd")), toElement("<ol><li class='odd'>first</li><li class='even'>second</li></ol>"));
        assertMatches("matching all children", hasSelector("ol > li", hasClassName("even"), hasClassName("odd")), toElement("<ol><li class='odd'>first</li><li class='even'>second</li></ol>"));
        assertDoesNotMatch("child does not match", hasSelector("#content", hasTag("div")), toElement("<span id='content'>content</span>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("has selector \"#content\"", hasSelector("#content"));
        assertDescription("has selector \"#content\" (a collection containing element with tag \"div\")", hasSelector("#content", HasTag.hasTag(equalTo("div"))));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("no selector \"ul li\"", hasSelector("ul li"), toElement("<ol><li>first</li><li>second</li></ol>"));
        assertMismatchDescription("#content a collection containing element with tag \"div\" element tag was \"span\"", hasSelector("#content", HasTag.hasTag(equalTo("div"))), toElement("<span id='content'>content</span>"));
    }
}