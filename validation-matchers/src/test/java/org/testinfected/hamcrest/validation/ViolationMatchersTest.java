package org.testinfected.hamcrest.validation;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testinfected.hamcrest.validation.FakeConstraintViolation.aViolation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.testinfected.hamcrest.validation.FakePath.pathAlong;
import static org.testinfected.hamcrest.validation.ViolationMatchers.violates;
import static org.testinfected.hamcrest.validation.ViolationMatchers.violation;

public class ViolationMatchersTest extends AbstractMatcherTest {

    ConstraintViolation<Object> shouldMatch = aViolation().describedAs("should match");
    ConstraintViolation<Object> wontMatch = aViolation().describedAs("won't match");

    @Override protected Matcher<?> createMatcher() {
        return violates(anything());
    }

    @Test public void
    providesConvenientShortcutForMatchingAViolationInACollection() {
        assertMatches("does not match violation", violates(equalTo(shouldMatch)), asSet(shouldMatch, wontMatch));
    }

    @Test public void
    doesNotMatchViolationSetWhenNoViolationIsMatched() {
        assertDoesNotMatch("matches a different violation", violates(equalTo(shouldMatch)), asSet(wontMatch));
    }

    @Test public void
    doesNotMatchEmptyViolationSet() {
        assertDoesNotMatch("matches non-existing violation", violates(), asSet());
    }

    @Test public void
    providesConvenientShortcutForMatchingAViolationAgainstSeveralMatchers() {
        assertMatches("does not match violation", violation(equalTo(shouldMatch), hasToString("should match")), shouldMatch);
    }

    @Test public void
    providesConvenientShortcutForMatchingOnPathProperty() {
        assertMatches("does not match path", ViolationMatchers.<Object>on("path.to.expression"), aViolation().on(pathAlong("path", "to", "expression")));
        assertDoesNotMatch("matches different path", ViolationMatchers.<Object>on("expression"), aViolation().on(pathAlong("mismatch")));
    }

    @Test public void
    providesConvenientShortcutForMatchingOnMessageTemplate() {
        assertMatches("does not match message", ViolationMatchers.<Object>withError("error.key"), aViolation().withMessageTemplate("error.key"));
        assertDoesNotMatch("matches a different message", ViolationMatchers.<Object>withError("error.key"), aViolation().withMessageTemplate("mismatched.key"));
    }

    private <T> Set<ConstraintViolation<T>> asSet(ConstraintViolation<T>... violations) {
        Set<ConstraintViolation<T>> set = new HashSet<ConstraintViolation<T>>();
        set.addAll(Arrays.asList(violations));
        return set;
    }
}
