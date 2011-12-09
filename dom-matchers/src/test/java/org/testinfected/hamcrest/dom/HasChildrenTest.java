package org.testinfected.hamcrest.dom;

import com.threelevers.css.Selector;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static com.threelevers.css.DocumentBuilder.dom;
import static org.testinfected.hamcrest.dom.HasChildren.hasChildren;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class HasChildrenTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasChildren(withTag("li"));
    }

    public void testMatchesWhenAllChildrenElementsMatch() {
        assertMatches("correct children", hasChildren(DomMatchers.withText("should match"), DomMatchers.withText("should match too")), anElement("<ol><li>should match</li><li>should match too</li></ol>"));
        assertDoesNotMatch("incorrect children", hasChildren(withTag("span")), anElement("<div><p>should not match</p></div>"));
        assertDoesNotMatch("one incorrect child", hasChildren(withTag("span"), withTag("span")), anElement("<div><p>should fail match</p><span>should match</span></div>"));
        assertDoesNotMatch("missing children", hasChildren(withTag("span")), anElement("<div></div>"));
    }

    public void testProvidesConvenientMethodForMatchingOneChildAmongstChildren() {
        assertMatches("single element", HasChildren.hasChild(withTag("span")), anElement("<div><p>won't match</p><span>should match</span></ol>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("an element with children iterable over [an element with content text \"should match\"]", hasChildren(DomMatchers.withText("should match")));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element children item 0: element content text was \"does not match\"", hasChildren(DomMatchers.withText("should not match")), anElement("<div><p>does not match</p></div>"));
    }

    private Element anElement(String html) {
        return Selector.from(dom(html)).selectUnique("html > body > *");
    }
}