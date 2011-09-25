package com.pyxis.matchers.validation;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

import javax.validation.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class HasNodesAlongPath extends TypeSafeMatcher<Path> {
    private List<Matcher<? super Path.Node>> nodeMatchers;

    public HasNodesAlongPath(Matcher<? super Path.Node>... nodeMatchers) {
        this(Arrays.asList(nodeMatchers));
    }

    public HasNodesAlongPath(List<Matcher<? super Path.Node>> nodeMatchers) {
        this.nodeMatchers = nodeMatchers;
    }

    @Override protected boolean matchesSafely(Path path) {
        return IsIterableContainingInOrder.<Path.Node>contains(nodeMatchers).matches(path);
    }

    public void describeTo(Description description) {
        description.appendList("", "->", "", nodeMatchers);
    }

    public static HasNodesAlongPath path(String expression) {
        List<Matcher<? super Path.Node>> nodeMatchers = new ArrayList<Matcher<? super Path.Node>>();
        for (String component : expression.split("\\.")) {
            nodeMatchers.add(nodeWithName(component));
        }
        return new HasNodesAlongPath(nodeMatchers);
    }

    public static Matcher<? super Path.Node> nodeWithName(String name) {
        return nodeWithName(equalTo("".equals(name) ? null : name));
    }

    public static Matcher<? super Path.Node> nodeWithName(Matcher<? super String> nameMatcher) {
        return new FeatureMatcher<Path.Node, String>(nameMatcher, "", "") {
            @Override protected String featureValueOf(Path.Node actual) {
                return actual.getName();
            }
        };
    }
}
