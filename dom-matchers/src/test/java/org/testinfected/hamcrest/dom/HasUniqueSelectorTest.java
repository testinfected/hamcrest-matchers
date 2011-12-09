package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.Matchers.equalTo;
import static org.testinfected.hamcrest.dom.HasUniqueSelector.hasUniqueSelector;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class HasUniqueSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasUniqueSelector("#content");
    }
    
    public void testMatchesWhenASingleChildMatchesSelector() {
        assertMatches("single element", hasUniqueSelector("#content"), dom("<div id='content'>content</div>"));
        assertDoesNotMatch("element not found", hasUniqueSelector("#content"), dom("<div>content</div>"));
        assertDoesNotMatch("element found more than once", hasUniqueSelector("li"), dom("<ol><li>first</li><li>second</li></ol>"));
    }

    public void testMatchesSelectedChildAgainstGivenMatcher() {
        assertMatches("matching child", hasUniqueSelector("#content", withTag("div")), dom("<div id='content'>content</div>"));
        assertDoesNotMatch("child does not match", hasUniqueSelector("#content", withTag("div")), dom("<span id='content'>content</span>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("has unique selector \"#content\" element with tag \"div\"", hasUniqueSelector("#content", withTag(equalTo("div"))));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("2 selector(s) \"li\"", hasUniqueSelector("li"), dom("<ol><li>first</li><li>second</li></ol>"));
    }
}
