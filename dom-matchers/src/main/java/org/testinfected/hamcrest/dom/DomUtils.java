package org.testinfected.hamcrest.dom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.NodeSelectorException;
import se.fishtank.css.selectors.dom.DOMNodeSelector;

import java.util.HashSet;
import java.util.Set;

public class DomUtils {
    public static Set<Node> selectNodes(Element root, String selector) {
        try {
            return new DOMNodeSelector(root).querySelectorAll(selector);
        } catch (NodeSelectorException e1) {
            return new HashSet<Node>();
        }
    }
}
