package org.testinfected.hamcrest.dom;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HasRootElement extends FeatureMatcher<Document, Element> {

    public HasRootElement(Matcher<? super Element> elementMatcher) {
        super(elementMatcher, "a document", "document");
    }

    @Override
    protected Element featureValueOf(Document actual) {
        return actual.getDocumentElement();
    }

    @Factory
    public static Matcher<? super Document> hasRootElement(Matcher<? super Element> elementMatcher) {
        return new HasRootElement(elementMatcher);
    }
}
