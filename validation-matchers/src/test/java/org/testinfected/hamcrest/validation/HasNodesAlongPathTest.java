package org.testinfected.hamcrest.validation;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

public class HasNodesAlongPathTest extends AbstractMatcherTest {

    HasNodesAlongPath simplePath = HasNodesAlongPath.path("property");
    FakePath shouldMatchSimplePath = FakePath.pathAlong("property");
    HasNodesAlongPath composedPath = HasNodesAlongPath.path("path.to.property");
    FakePath shouldMatchComposedPath = FakePath.pathAlong("path", "to", "property");
    FakePath shouldNotMatch = FakePath.pathAlong("notMatched");
    FakePath shouldMatchClassPath= FakePath.pathAlong("");
    HasNodesAlongPath classPath = HasNodesAlongPath.path("");

    @Override protected Matcher<?> createMatcher() {
        return composedPath;
    }

    public void testMatchesPathComposedOfNodesLeadingToProperty() {
        assertMatches("simple path", simplePath, shouldMatchSimplePath);
        assertMatches("composed path", composedPath, shouldMatchComposedPath);
    }

    public void testWillNotMatchIfPathComponentIsNotPresent() {
        assertDoesNotMatch("simple path", simplePath, shouldNotMatch);
    }

    public void testBlankPathMatchesPathOfClass() {
    	 assertMatches("class path", classPath, shouldMatchClassPath);
    }
    
     public void testHasHumanReadableDescription() {
        assertDescription(" \"property\"", simplePath);        
        assertDescription(" \"path\"-> \"to\"-> \"property\"", composedPath);        
    }
}
