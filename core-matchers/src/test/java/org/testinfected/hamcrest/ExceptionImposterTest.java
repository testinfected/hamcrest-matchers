package org.testinfected.hamcrest;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ExceptionImposterTest {
    private Exception original;

    @Test
    public void leavesUncheckedExceptionsUnchanged() {
        original = new RuntimeException();
        assertSame(original, ExceptionImposter.imposterize(original));
    }

    @Test
    public void imposterizesCheckedExceptionsAndKeepsAReference() {
        original = new Exception();
        RuntimeException imposter = ExceptionImposter.imposterize(original);
        assertTrue("wrong type", imposter instanceof ExceptionImposter);
        assertSame("real exception", original, ((ExceptionImposter) imposter).getRealException());
    }

    @Test
    public void mimicsImposterizedExceptionToStringOutput() {
        original = new Exception("Detail message");
        RuntimeException imposter = ExceptionImposter.imposterize(original);
        assertEquals("string representation", original.toString(), imposter.toString());
    }

    @Test
    public void copiesImposterizedExceptionStackTrace() {
        original = new Exception("Detail message");
        original.fillInStackTrace();
        RuntimeException imposter = ExceptionImposter.imposterize(original);
        assertArrayEquals("stack trace", original.getStackTrace(), imposter.getStackTrace());
    }

    @Test
    public void mimicsImposterizedExceptionStackTraceOutput() {
        original = new Exception("Detail message");
        original.fillInStackTrace();
        RuntimeException imposter = ExceptionImposter.imposterize(original);
        assertEquals("stack trace output", captureStackTrace(original), captureStackTrace(imposter));
    }

    private String captureStackTrace(Exception exception) {
        StringWriter capture = new StringWriter();
        exception.printStackTrace(new PrintWriter(capture));
        capture.flush();
        return capture.toString();
    }
}
