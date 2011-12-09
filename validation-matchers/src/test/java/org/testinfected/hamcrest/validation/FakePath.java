package org.testinfected.hamcrest.validation;

import javax.validation.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.testinfected.hamcrest.validation.FakeNode.aNode;

public class FakePath implements Path {

    public Iterable<Node> nodes;

    public static FakePath pathAlong(String... components) {
        Collection<Node> nodes = new ArrayList<Node>();
        for (String component : components) {
            nodes.add(aNode().withName(component));
        }
        return new FakePath(nodes);
    }

    public FakePath(Iterable<Node> nodes) {
        this.nodes = nodes;
    }

    public Iterator<Node> iterator() {
        return nodes.iterator();
    }
}

