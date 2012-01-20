/*
 * Copyright (c) 2000-2006 hamcrest.org
 */
package org.testinfected.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractMatcherTest {

    protected abstract Matcher<?> createMatcher();

    public static <T> void assertMatches(String message, Matcher<? super T> c, T arg) {
        Assert.assertTrue(message, c.matches(arg));
    }

    public static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
        Assert.assertFalse(message, c.matches(arg));
    }

    public static void assertDescription(String expected, Matcher<?> matcher) {
        Description description = new StringDescription();
        description.appendDescriptionOf(matcher);
        assertEquals("description", expected, description.toString());
    }

    public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, T arg) {
        Description description = new StringDescription();
        Assert.assertFalse("matches item", matcher.matches(arg));
        matcher.describeMismatch(arg, description);
        assertEquals("mismatch description", expected, description.toString());
    }

    @Test public void
    isNullSafe() {
       // should not throw a NullPointerException
       createMatcher().matches(null);
    }

    @Test public void
    copesWithUnknownTypes() {
        // should not throw ClassCastException
        createMatcher().matches(new UnknownType());
    }

    public static class UnknownType {
    }
}
