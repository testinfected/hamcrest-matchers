package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.testinfected.hamcrest.dom.HasSelector.hasSelector;
import static org.testinfected.hamcrest.dom.WithTag.withTag;
import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.Matchers.equalTo;

public class HasSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSelector("#content");
    }

    public void testMatchesWhenAtLeastAChildMatchesSelector() {
        assertMatches("single element", hasSelector("#content"), dom("<div id='content'>content</div>"));
        assertMatches("multiple elements", hasSelector("li"), dom("<ol><li>first</li><li>second</li></ol>"));
        assertDoesNotMatch("element not found", hasSelector("#content"), dom("<div>content</div>"));
    }

    public void testMatchSelectedChildrenAgainstGivenMatcher() {
        assertMatches("matching child", hasSelector("#content", withTag("div")), dom("<div id='content'>content</div>"));
        assertMatches("matching children", hasSelector("ol > li", withTag("li")), dom("<ol><li>first</li><li>second</li></ol>"));
        assertDoesNotMatch("child does not match", hasSelector("#content", withTag("div")), dom("<span id='content'>content</span>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("has selector \"#content\" (a collection containing element with tag \"div\")", hasSelector("#content", withTag(equalTo("div"))));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("no selector \"ul li\"", hasSelector("ul li"), dom("<ol><li>first</li><li>second</li></ol>"));
        assertMismatchDescription("#content a collection containing element with tag \"div\" element tag was \"SPAN\"", hasSelector("#content", withTag(equalTo("div"))), dom("<span id='content'>content</span>"));
    }
}