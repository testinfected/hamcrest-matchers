package org.testinfected.hamcrest.validation;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

public class HasNodesAlongPathTest extends AbstractMatcherTest {

    HasNodesAlongPath simplePath = HasNodesAlongPath.path("property");
    FakePath shouldMatchSimplePath = FakePath.pathAlong("property");
    HasNodesAlongPath composedPath = HasNodesAlongPath.path("path.to.property");
    FakePath shouldMatchComposedPath = FakePath.pathAlong("path", "to", "property");
    FakePath shouldNotMatch = FakePath.pathAlong("notMatched");
    HasNodesAlongPath classPath = HasNodesAlongPath.path("");
    FakePath shouldMatchClassPath= FakePath.pathAlong("");

    @Override protected Matcher<?> createMatcher() {
        return composedPath;
    }

    @Test public void
    matchesPathToProperty() {
        assertMatches("simple path", simplePath, shouldMatchSimplePath);
        assertMatches("composed path", composedPath, shouldMatchComposedPath);
    }

    @Test public void
    wontMatchIfPathComponentIsNotFound() {
        assertDoesNotMatch("simple path", simplePath, shouldNotMatch);
    }

    @Test public void
    blankPathMatchesPathOfClass() {
    	 assertMatches("class path", classPath, shouldMatchClassPath);
    }
    
    @Test public void
    hasHumanReadableDescription() {
        assertDescription(" \"property\"", simplePath);        
        assertDescription(" \"path\"-> \"to\"-> \"property\"", composedPath);        
    }
}
