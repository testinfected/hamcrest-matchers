package org.testinfected.hamcrest.dom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.NodeSelectorException;
import se.fishtank.css.selectors.dom.DOMNodeSelector;

import java.util.Set;

public class Selector {
    private Node root;

    private Selector(Node root) {
        this.root = root;
    }

    public static Selector from(Element root) {
        return new Selector(root);
    }

    public Set<Node> selectAll(String selector) {
        try {
            return new DOMNodeSelector(root).querySelectorAll(selector);
        } catch (NodeSelectorException e) {
            throw new IllegalArgumentException("Selector expression is invalid: " + selector, e);
        }
    }
}
