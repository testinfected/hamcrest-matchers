package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.testinfected.hamcrest.dom.DomMatchers.inOrder;

public class HasChildren extends FeatureMatcher<Element, Iterable<Element>> {

    public HasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        super(childrenMatcher, "an element with children", "element children");
    }

    protected Iterable<Element> featureValueOf(Element actual) {
        return Elements.children(actual);
    }

    @Factory
    public static Matcher<Element> hasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        return new HasChildren(childrenMatcher);
    }

    @Factory
    public static Matcher<Element> hasChildren(Matcher<? super Element>... childrenMatchers) {
        return hasChildren(inOrder(childrenMatchers));
    }

    @SuppressWarnings("unchecked")
    @Factory
    public static Matcher<Element> hasChild(Matcher<Element> childMatcher) {
        return hasChildren(Matchers.<Element>hasItems(childMatcher));
    }

    private static class Elements {
        public static List<Element> children(Element element) {
            List<Element> children = new ArrayList<Element>();
            NodeList descendantNodes = element.getChildNodes();
            for (int i = 0; i < descendantNodes.getLength(); i++) {
                Node childNode = descendantNodes.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) children.add((Element) childNode);
            }
            return children;
        }
    }
}
