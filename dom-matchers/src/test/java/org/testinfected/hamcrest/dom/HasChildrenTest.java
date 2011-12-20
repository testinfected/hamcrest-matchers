package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.testinfected.hamcrest.dom.Documents.toElement;
import static org.testinfected.hamcrest.dom.DomMatchers.withText;
import static org.testinfected.hamcrest.dom.HasChildren.hasChild;
import static org.testinfected.hamcrest.dom.HasChildren.hasChildren;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class HasChildrenTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasChildren(withTag("li"));
    }

    public void testMatchesWhenAllChildrenElementsMatch() {
        assertMatches("correct children", hasChildren(withText("should match"), withText("should match too")), anElement("<ol><li>should match</li><li>should match too</li></ol>"));
        assertDoesNotMatch("incorrect children", hasChildren(withTag("span")), anElement("<div><p>should not match</p></div>"));
        assertDoesNotMatch("one incorrect child", hasChildren(withTag("span"), withTag("span")), anElement("<div><p>should fail match</p><span>should match</span></div>"));
        assertDoesNotMatch("missing children", hasChildren(withTag("span")), anElement("<div></div>"));
    }

    public void testOnlyConsidersFirstLevelChildren() {
        assertDoesNotMatch("grand children", hasChild(withTag("span")), anElement("<div><p><span>should not match</span></p></div>"));
    }

    public void testProvidesConvenientMethodForMatchingOneChildAmongstChildren() {
        assertMatches("single element", hasChild(withTag("span")), anElement("<div><p>won't match</p><span>should match</span></div>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("an element with children iterable over [an element with content text \"should match\"]", hasChildren(withText("should match")));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element children item 0: element content text was \"does not match\"", hasChildren(withText("should not match")), anElement("<div><p>does not match</p></div>"));
    }

    private Element anElement(String html) {
        return toElement(html);
    }
}