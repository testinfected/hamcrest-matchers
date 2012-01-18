package org.testinfected.hamcrest.dom;

import static org.hamcrest.Matchers.allOf;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

/**
 * A collection of hamcrest matchers to validate DOM elements
 * (objects of type {@link org.w3c.dom.Element}).
 */
public class DomMatchers {

    private DomMatchers() {}

    /**
     * Checks that an {@link org.w3c.dom.Element} matches a CSS3 selector with the specified matchers matching the subjects of the
     * selector in any order. This is equivalent to <code>hasSelector(selector, matchesInAnyOrder(subjectsMatchers))</code>.
     *
     * @param selector the CSS3 selector expression to match against the element
     * @param subjectsMatchers matchers to match the elements represented by the selector
     */
    public static Matcher<Element> hasSelector(String selector, Matcher<? super Element>... subjectsMatchers) {
        return HasSelector.hasSelector(selector, subjectsMatchers);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} matches a CSS3 selector with the specified matchers matching the subjects of the selector.
     *
     * @param selector the CSS3 selector expression to match against the element
     * @param subjectsMatcher matchers to match the elements represented by the selector
     */
    public static Matcher<Element> hasSelector(String selector, Matcher<Iterable<Element>> subjectsMatcher) {
        return HasSelector.hasSelector(selector, subjectsMatcher);
    }


    /**
     * Checks that a collection of {@link org.w3c.dom.Element}a is of a specified size.
     *
     * @param size the expected number of elements
     */
    public static Matcher<Iterable<Element>> hasSize(int size) {
        return Matchers.iterableWithSize(size);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} matches a CSS3 selector and that the selector represents a single
     * child element.
     *
     * @param selector the CSS3 selector expression to match against the element
     */
    public static Matcher<Element> hasUniqueSelector(String selector) {
        return HasUniqueSelector.hasUniqueSelector(selector);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} matches a CSS3 selector with the specified matcher matching the only
     * subject of the selector.
     *
     * @param selector the CSS3 selector expression to match against the element
     * @param subjectMatcher the matcher to match the single element represented by the selector
     */
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element> subjectMatcher) {
        return HasUniqueSelector.hasUniqueSelector(selector, subjectMatcher);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} matches a CSS3 selector and that the single element represented by the selector
     * is matched by all specified matchers.
     * This is a equivalent to <code>hasUniqueSelector(selector, anElement(subjectMatchers))</code>.
     *
     * @param selector the CSS3 selector expression to match against the element
     * @param subjectMatchers the matchers to match the single element represented by the selector
     */
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element>... subjectMatchers) {
        return HasUniqueSelector.hasUniqueSelector(selector, subjectMatchers);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} is not matched by the specified CSS3 selector expression.
     * @param selector the CSS3 selector expression
     */
    public static Matcher<Element> hasNoSelector(String selector) {
    	return HasNoSelector.hasNoSelector(selector);
    }

    /**
     * Checks that a collection contains {@link org.w3c.dom.Element}s that are matched in order by the specified matchers.
     * <p>
     * Each element in the collection must be matched and each specified matcher must match an element.
     * Matching occurs in order, so the first element in the collection is matched against the first specified matcher,
     * the second element is matched by second matcher argument, and so on.
     * </p>
     * @see DomMatchers#matches(java.util.List)
     * @param elementsMatchers the matchers to match the collection of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> matches(Matcher<? super Element>... elementsMatchers) {
        return matches(Arrays.asList(elementsMatchers));
    }

    /**
     * <p>
     * Checks that a collection contains {@link org.w3c.dom.Element}s that are matched in order
     * by the specified list of matchers.
     * </p>
     * <p>
     * Each element in the collection must be matched and each specified matcher must match an element.
     * Matching occurs in order, so the first element in the collection is matched against the first matcher in the list,
     * the second element is matched by second matcher, and so on.
     * </p>
     * <p>
     * Note: As of hamcrest 1.3, the <code>hasItems</code> family of matchers return <code>Matcher&lt;Iterable&lt;? extends T&gt;&gt;</code>
     * whereas the <code>contains</code> family of matchers return <code>Iterable&lt;Matcher&lt;T&gt;&gt;</code>.
     * Unfortunately, this makes them impossible to combine as arguments to
     * {@link DomMatchers#hasSelector(String, org.hamcrest.Matcher)} without this method.
     * <p>
     *
     * @param elementsMatcher the list fo matchers to match the collection of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> matches(List<Matcher<? super Element>> elementsMatcher) {
        // Let's force Matcher<Iterable<Element>>, since Element is an interface
        return new IsIterableContainingInOrder(elementsMatcher);
    }

    /**
     * Checks that a collection contains {@link org.w3c.dom.Element}s that are matched
     * in any order by the specified matchers.
     * <p>
     * Each element in the collection must be matched and each specified matcher must match an element.
     * Matching can occur in any order.
     * </p>
     * @see DomMatchers#matchesInAnyOrder(java.util.List)
     * @param elementsMatchers the matchers to match the collection of {@link org.w3c.dom.Element}s
     */

    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> matchesInAnyOrder(Matcher<? super Element>... elementsMatchers) {
        return matchesInAnyOrder(Arrays.asList(elementsMatchers));
    }

    /**
     * Checks that a collection contains {@link org.w3c.dom.Element}s that are matched
     * in any order by the specified list of matchers.
     * <p>
     * Each element in the collection must be matched and each specified matcher must match an element.
     * Matching can occur in any order.
     * </p>
     * <p>
     * Note: As of hamcrest 1.3, the <code>hasItems</code> family of matchers return <code>Matcher&lt;Iterable&lt;? extends T&gt;&gt;</code>
     * whereas the <code>contains</code> family of matchers return <code>Iterable&lt;Matcher&lt;T&gt;&gt;</code>.
     * Unfortunately, this makes them impossible to combine as arguments to
     * {@link DomMatchers#hasSelector(String, org.hamcrest.Matcher)} without this method.
     * <p>
     *
     * @param elementsMatcher the list fo matchers to match the collection of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> matchesInAnyOrder(List<Matcher<? super Element>> elementsMatcher) {
        // Let's force Matcher<Iterable<Element>>, since Element is an interface
        return new IsIterableContainingInAnyOrder(elementsMatcher);
    }

    /**
     * Checks that a collection of {@link org.w3c.dom.Element}s contains, in any order, at least one matched element for
     * each specified matcher.
     *
     * @param elementsMatchers matchers to match {@link org.w3c.dom.Element}s in the collection
     */
    public static Matcher<Iterable<Element>> includes(Matcher<? super Element>... elementsMatchers) {
        return Matchers.hasItems(elementsMatchers);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has the specified tag.
     */
    public static Matcher<Element> hasTag(String tagName) {
        return HasTag.hasTag(tagName);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} content is equal to the specified text.
     */
    public static Matcher<Element> hasText(String contentText) {
        return HasContentText.hasContent(contentText);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} content text matches the specified matcher.
     */
    public static Matcher<Element> hasText(Matcher<? super String> contentMatcher) {
        return HasContentText.hasContent(contentMatcher);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} content contains only blank characters.
     */
    public static Matcher<Element> hasBlankText() {
    	return HasContentText.hasBlankContent();
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has an attribute with the specified value.
     *
     * @param name the name of the attribute.
     * @param value the expected value of the attribute
     */
    public static Matcher<Element> hasAttribute(String name, String value) {
        return HasAttribute.hasAttribute(name, value);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has an attribute whose value matches the specified matcher.
     *
     * @param name the name of the attribute to match
     * @param valueMatcher matcher for matching the attribute's value.
     */
    public static Matcher<Element> hasAttribute(String name, Matcher<? super String> valueMatcher) {
        return HasAttribute.hasAttribute(name, valueMatcher);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has the specified attribute, whatever its value.
     *
     * @param name the name of the attribute
     */
    public static Matcher<Element> hasName(String name) {
        return HasAttribute.hasName(name);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has an id attribute with the specified value.
     *
     * @param id the expected value of the id attribute
     */
    public static Matcher<Element> hasId(String id) {
        return HasAttribute.hasId(id);
    }

    /**
     * Checks that an {@link org.w3c.dom.Element} has the specified CSS class.
     * Note that the element can have other classes as well.
     *
     * @param className the expected class the element
     */
    public static Matcher<Element> hasClassName(String className) {
        return HasAttribute.hasClassName(className);
    }

    /**
     * Checks that children of an {@link org.w3c.dom.Element} match in order the specified matchers.
     * The number of matchers must be the same as the number of children.
     * <p>
     * Matching occurs in order, so the first child is matched against the first matcher,
     * the second child against second matcher, and so on.
     * </p>
     */
    public static Matcher<Element> hasChildren(Matcher<? super Element>... childrenMatchers) {
        return HasChildren.hasChildren(childrenMatchers);
    }

    /**
     * Checks that children of an {@link org.w3c.dom.Element} match in order the specified matchers.
     * The number of matchers must be the same as the number of children.
     * <p>
     * Matching occurs in order, so the first child is matched against the first matcher,
     * the second child against second matcher, and so on.
     * </p>
     */
    public static Matcher<Element> hasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        return HasChildren.hasChildren(childrenMatcher);
    }

    /**
     * Checks that at least one child of an {@link org.w3c.dom.Element} is matched against
     * the given matcher.
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Element> hasChild(Matcher<? super Element> childMatcher) {
        return HasChildren.hasChild(childMatcher);
    }

    /**
     * Combines a group of matchers for matching an {@link org.w3c.dom.Element}.
     */
    public static Matcher<Element> anElement(final Matcher<? super Element>... elementMatchers) {
        return allOf(elementMatchers);
    }
}
