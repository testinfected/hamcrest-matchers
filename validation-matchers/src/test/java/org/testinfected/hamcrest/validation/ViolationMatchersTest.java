package org.testinfected.hamcrest.validation;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

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

    public void testProvidesConvenientShortcutForMatchingAViolationInACollection() {
        assertMatches("a match", violates(equalTo(shouldMatch)), asSet(shouldMatch, wontMatch));
    }

    public void testDoesNotMatchViolationSetWhenNoViolationIsMatched() {
        assertDoesNotMatch("no match", violates(equalTo(shouldMatch)), asSet(wontMatch));
    }

    public void testDoesNotMatchEmptyViolationSet() {
        assertDoesNotMatch("no violation", violates(), asSet());
    }

    public void testProvidesConvenientShortcutToMatchAViolationAgainstSeveralMatchers() {
        assertMatches("multiple matchers", violation(equalTo(shouldMatch), hasToString("should match")), shouldMatch);
    }

    public void testProvidesConvenientShortcutToMatchOnPathProperty() {
        assertMatches("path", ViolationMatchers.<Object>on("path.to.expression"), aViolation().on(pathAlong("path", "to", "expression")));
        assertDoesNotMatch("path mismatch", ViolationMatchers.<Object>on("expression"), aViolation().on(pathAlong("mismatch")));
    }

    public void testProvidesConvenientShortcutToMatchOnMessageTemplate() {
        assertMatches("message", ViolationMatchers.<Object>withError("error.key"), aViolation().withMessageTemplate("error.key"));
        assertDoesNotMatch("message mismatch", ViolationMatchers.<Object>withError("error.key"), aViolation().withMessageTemplate("mismatched.key"));
    }

    private <T> Set<ConstraintViolation<T>> asSet(ConstraintViolation<T>... violations) {
        Set<ConstraintViolation<T>> set = new HashSet<ConstraintViolation<T>>();
        set.addAll(Arrays.asList(violations));
        return set;
    }
}
