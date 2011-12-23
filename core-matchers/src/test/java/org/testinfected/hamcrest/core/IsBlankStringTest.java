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
		assertMatches("a blank string", isBlank(), " ");
	}

	@Test public void
    matchesAStringContainingTabOrNewlineCharacters() {
		assertMatches("a blank string with tabs and newlines", isBlank(), "\t\n");
	}
	
	@Test public void
    matchesAStringContainingUnicodeBlankCharacters() {
		assertMatches("a blank string with unicode blanks", isBlank(), "" + (char) 160);
	}

	@Test public void
    doesNotMatchAStringContainingNonBlankCharacters() {
		assertDoesNotMatch("a non-blank string", isBlank(), " x ");
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
