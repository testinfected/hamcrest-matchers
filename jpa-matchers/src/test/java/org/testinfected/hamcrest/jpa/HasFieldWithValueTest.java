package org.testinfected.hamcrest.jpa;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testinfected.hamcrest.jpa.HasFieldWithValue.hasField;

public class HasFieldWithValueTest extends AbstractMatcherTest {

    AnObject shouldMatch = new AnObject("is expected");
    AnObject shouldNotMatch = new AnObject("not expected");

    @Override protected Matcher<?> createMatcher() {
        return hasField("irrelevant", anything());
    }

    @Test public void
    matchesWhenFieldValueMatches() {
        assertMatches("does not match field", hasField("field", equalTo("is expected")), shouldMatch);
        assertMismatchDescription("\"field\" was \"not expected\"",
                                  hasField("field", equalTo("is expected")), shouldNotMatch);
    }

    @Test public void
    matchesPresenceOfField() {
        assertMatches("does not match field", hasField("field"), shouldMatch);
    }

    @Test public void
    doesNotMatchWhenFieldIsMissing() {
        assertDoesNotMatch("matches a different field", hasField("field", equalTo("is expected")), shouldNotMatch);
        assertMismatchDescription("no field \"doh\"", hasField("doh", anything()), shouldNotMatch);
    }

    @Test public void
    hsHumanReadableDescription() {
        assertDescription("has field \"field\": \"value\"", hasField("field", equalTo("value")));
    }

    public static class AnObject {
        private String field;

        public AnObject(String field) {
            this.field = field;
        }
    }
}
