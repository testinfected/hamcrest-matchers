package org.testinfected.hamcrest.dom;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static com.threelevers.css.DocumentBuilder.doc;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.WithRootElement.withRootElement;
import static org.testinfected.hamcrest.dom.WithTag.withTag;

public class WithRootElementTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withRootElement(withTag("html"));
    }

    public void testMatchesRootDocumentElement() {
        assertMatches("correct element", withRootElement(withTag("html")), doc("<html></html>"));
        assertDoesNotMatch("incorrect element", withRootElement(withTag("xml")), doc("<html></html>"));
    }

    public void testHasAReadableDescription() {
        assertDescription("a document element with tag \"html\"", withRootElement(withTag(equalTo("html"))));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("document element tag was \"HTML\"", withRootElement(withTag(equalTo("xml"))), doc("<html></html>"));
    }

}