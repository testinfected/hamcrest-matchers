package org.testinfected.hamcrest.validation;

import javax.validation.Path;


public class FakeNode implements Path.Node {

    public String name;

    public static FakeNode aNode() {
        return new FakeNode();
    }

    public FakeNode withName(String nodeName) {
        name = "".equals(nodeName) ? null : nodeName;
        return this;
    }

    public Object getKey() {
        return null;
    }

    public Integer getIndex() {
        return null;
    }

    public boolean isInIterable() {
        return false;
    }

    public String getName() {
        return name;
    }
}

