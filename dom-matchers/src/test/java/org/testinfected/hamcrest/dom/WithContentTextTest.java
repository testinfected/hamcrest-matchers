package org.testinfected.hamcrest.dom;

import com.threelevers.css.Selector;
import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static com.threelevers.css.DocumentBuilder.dom;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.dom.WithContentText.withContent;

public class WithContentTextTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return withContent("text");
    }

    public void testMatchesWhenElementHasMatchingTagName() {
        assertMatches("correct content", withContent(equalTo("text")), anElementWithText("text"));
        assertDoesNotMatch("incorrect content", withContent(equalTo("text")), anElementWithText("other text"));
    }

    public void testProvidesConvenientShortcutForMatchingIdenticalText() {
        assertMatches("same text", withContent("text"), anElementWithText("text"));
    }

    public void testHasAReadableDescription() {
        assertDescription("an element with content text \"expected\"", withContent(equalTo("expected")));
    }

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("element content text was \"different\"", withContent(equalTo("expected")), anElementWithText("different"));
    }

    private Element anElementWithText(String content) {
        return element(String.format("<div>%s</div>", content));
    }

    private Element element(String html) {
        return Selector.from(dom(html)).selectUnique("html > body > *");
    }
}