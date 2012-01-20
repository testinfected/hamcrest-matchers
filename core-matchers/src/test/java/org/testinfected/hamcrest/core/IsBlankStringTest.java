package org.testinfected.hamcrest.core;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.hamcrest.AbstractMatcherTest;

import static org.testinfected.hamcrest.core.IsBlankString.isBlank;

public class IsBlankStringTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return isBlank();
	}

    @Test public void
    matchesAStringContainingWhitespaceCharacters() {
		assertMatches("does not match blank", isBlank(), " ");
	}

	@Test public void
    matchesAStringContainingTabOrNewlineCharacters() {
		assertMatches("does not match tabs and newlines", isBlank(), "\t\n");
	}
	
	@Test public void
    matchesAStringContainingUnicodeBlankCharacters() {
		assertMatches("does not match unicode blanks", isBlank(), "" + (char) 160);
	}

	@Test public void
    doesNotMatchAStringContainingNonBlankCharacters() {
		assertDoesNotMatch("matches non-blank", isBlank(), " x ");
	}
	
	@Test public void
    hasAReadableDescription() {
		assertDescription("a blank string", isBlank());
	}

    @Test public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("not blank", isBlank(), "not blank");
    }
}
