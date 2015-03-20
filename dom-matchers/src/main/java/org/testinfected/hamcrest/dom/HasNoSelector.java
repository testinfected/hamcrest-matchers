package org.testinfected.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Set;

public class HasNoSelector extends TypeSafeDiagnosingMatcher<Element> {

    private String selector;

    public HasNoSelector(String selector) {
        this.selector = selector;
    }

    @Override
    protected boolean matchesSafely(Element doc, Description mismatchDescription) {
        Set<Node> selected = DomUtils.selectNodes(doc, selector);
        if (selected.size() != 0) {
            Node match = selected.iterator().next();
            mismatchDescription.appendText("found element \"" + match.getNodeName() + "\"");
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendText("has no selector \"" + this.selector + "\"");
    }

    @Factory
    public static Matcher<Element> hasNoSelector(String selector) {
        return new HasNoSelector(selector);
    }
}
