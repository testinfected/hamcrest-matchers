package org.testinfected.hamcrest.core;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.testinfected.hamcrest.core.IsBlankString.isBlank;

public class IsBlankStringTest extends AbstractMatcherTest {

	@Override
	protected Matcher<?> createMatcher() {
		return isBlank();
	}

	public void testShouldMatchAStringContainingWhitespaceCharacters() {
		assertMatches("a blank string", isBlank(), " ");
	}

	public void testShouldMatchAStringContainingTabOrNewlineCharacters() {
		assertMatches("a blank string with tabs and newlines", isBlank(), "\t\n");
	}
	
	public void testShouldMatchAStringContainingUnicodeBlankCharacters() {
		assertMatches("a blank string with unicode blanks", isBlank(), "" + (char) 160);
	}

	public void testShouldNotMatchAStringContainingNonBlankCharacters() {
		assertDoesNotMatch("a non-blank string", isBlank(), " x ");
	}
	
	public void testHasAReadableDescription() {
		assertDescription("a blank string", isBlank());
	}

    public void testHasAReadableMismatchDescription() {
        assertMismatchDescription("not blank", isBlank(), "not blank");
    }
}
