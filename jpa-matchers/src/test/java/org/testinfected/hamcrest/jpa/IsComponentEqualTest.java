package org.testinfected.hamcrest.jpa;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import javax.persistence.Embeddable;

import static org.testinfected.hamcrest.jpa.IsComponentEqual.componentEqualTo;
import static org.testinfected.hamcrest.jpa.SamePersistentFieldsAs.samePersistentFieldsAs;

public class IsComponentEqualTest extends AbstractMatcherTest {

    private static final Value aValue = new Value("expected");
    private static final Value aMatchingValue = new Value("expected");
    private static final ExampleComponent expectedComponent = new ExampleComponent("same", 1, aValue);
    private static final ExampleComponent shouldMatch = new ExampleComponent("same", 1, aMatchingValue);

    @Override protected Matcher<?> createMatcher() {
        return componentEqualTo(expectedComponent);
    }

    public void testMatchesWhenAllPersistentFieldsMatch() {
      assertMatches("matching fields", componentEqualTo(expectedComponent), shouldMatch);
    }

    public void testMatchesTwoNullComponents() {
      assertMatches("null match", componentEqualTo(null), null);
    }

    public void testMatchesAnExpectedNullToAComponentWithNullProperties() {
      assertMatches("null expected",
          componentEqualTo(null), new ExampleComponent(null, null, null));
    }

    public void testMatchesAComponentWithNullPropertiesToAnActualNull() {
      assertMatches("all null expected",
          componentEqualTo(new ExampleComponent(null, null, null)), null);
    }

    public void testReportsMismatchWhenActualComponentIsNull() {
      assertMismatchDescription("is null",
          componentEqualTo(expectedComponent), null);
    }

    public void testDescribesNullExpectationClearly() {
      assertDescription("null", componentEqualTo(null));
    }

    public void testHasHumanReadableDescription() {
      assertDescription("with fields [string: \"same\", integer: <1>, value: <expected>]", samePersistentFieldsAs(expectedComponent));
    }

    public static class Value {
        public Value(Object value) {
            this.value = value;
        }

        public final Object value;

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return value.equals(((Value) o).value);
        }
    }

    @Embeddable
    public static class ExampleComponent {

        private String string;
        private Integer integer;
        private Value value;

        public ExampleComponent(String string, Integer integer, Value value) {
            this.string = string;
            this.integer = integer;
            this.value = value;
        }
    }
}