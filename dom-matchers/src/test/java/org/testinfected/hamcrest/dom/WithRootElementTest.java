package org.testinfected.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.Documents.document;
import static org.testinfected.hamcrest.dom.WithRootElement.withRootElement;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class WithRootElementTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withRootElement(withTag("html"));
    }

    @Test public void
    matchesRootDocumentElement() {
        assertMatches("correct element", withRootElement(withTag("html")), document("<html></html>"));
        assertDoesNotMatch("incorrect element", withRootElement(withTag("xml")), document("<html></html>"));
    }

    @Test public void
    hasAReadableDescription() {
        assertDescription("a document element with tag \"html\"", withRootElement(withTag(equalTo("html"))));
    }

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("document element tag was \"html\"", withRootElement(withTag(equalTo("xml"))), document("<html></html>"));
    }

}