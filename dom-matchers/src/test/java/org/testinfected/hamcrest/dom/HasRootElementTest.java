package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.document;
import static org.testinfected.hamcrest.dom.HasRootElement.hasRootElement;
import static org.testinfected.hamcrest.dom.HasTag.hasTag;

public class HasRootElementTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasRootElement(hasTag("html"));
    }

    @Test public void
    matchesRootDocumentElement() {
        assertMatches("does not match root element", hasRootElement(hasTag("html")), document("<html></html>"));
        assertDoesNotMatch("matches different root element", hasRootElement(hasTag("xml")), document("<html></html>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("a document element with tag \"html\"", hasRootElement(hasTag(equalTo("html"))));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("document element tag was \"html\"", hasRootElement(hasTag(equalTo("xml"))), document("<html></html>"));
    }

}