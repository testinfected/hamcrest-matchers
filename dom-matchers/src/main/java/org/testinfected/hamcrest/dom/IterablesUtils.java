package org.testinfected.hamcrest.dom;

import java.util.Collection;
import java.util.Iterator;

public final class IterablesUtils {

    public static boolean isEmpty(Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }

    public static <T> T getOnlyElement(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }

        throw new IllegalArgumentException("expected only one element in the iterable but size was > 1.");
    }

    public static int size(Iterable<?> iterable) {
        return (iterable instanceof Collection) ? ((Collection<?>) iterable).size() : size(iterable.iterator());
    }

    private static int size(Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
