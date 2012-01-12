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
     * Checks that an element contains descendants referenced by the given CSS selector that
     * satisfy a list of matchers in order.
     * 
     * If the selector picks out more than one element, the selected elements will be matched
     * <strong>in order</strong> by the matchers provided as arguments (
     * i.e. the first element selected will be matched by the first matcher
     * argument, the second element by the second matcher argument, and so on ...)
     *
     * @param selector the selector expression to filter descendants of the element.
     * @param elementsMatchers matchers used to validate the list of descendants selected by the selector.
     */
    public static Matcher<Element> hasSelector(String selector, Matcher<? super Element>... elementsMatchers) {
        return HasSelector.hasSelector(selector, elementsMatchers);
    }

    /**
     * Checks that the an element contains descendants referenced by the given CSS selector that
     * satisfy a list of matchers in order.
     *
     * If the selector picks out more than one element, the selected elements will be matched
     * <strong>in order</strong> by the matchers provided as arguments (
     * i.e. the first element selected will be matched by the first matcher
     * argument, the second element by the second matcher argument, and so on ...)
     *
     * @param selector the selector expression to filter descendants of the element.
     * @param elementsMatcher matchers used to validate element(s) selected by the selector.
     */
    public static Matcher<Element> hasSelector(String selector, Matcher<Iterable<Element>> elementsMatcher) {
        return HasSelector.hasSelector(selector, elementsMatcher);
    }

    /**
     * Checks that an element contains one and only one descendant referenced by the given CSS selector.
     *
     * @param selector the selector expression to select a single descendant of the element.
     */
    public static Matcher<Element> hasUniqueSelector(String selector) {
        return HasUniqueSelector.hasUniqueSelector(selector);
    }

    /**
     * Checks that an element contains one and only one descendant referenced by the given CSS selector
     * and that this descendant satisfy the given matcher.
     *
     * @param selector the selector expression to select a single descendant of the element.
     * @param elementMatcher the matcher to validate the descendant found.
     */
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<Element> elementMatcher) {
        return HasUniqueSelector.hasUniqueSelector(selector, elementMatcher);
    }

    /**
     * Checks that an element contains a single descendant selected by the given CSS selector
     * and that this element is matched by <strong>all</strong> given matchers.
     *
     * @param selector the selector expression to filter descendants of the element.
     * @param elementMatchers matchers to validate the unique descendant found.
     */
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<Element>... elementMatchers) {
        return HasUniqueSelector.hasUniqueSelector(selector, allOf(elementMatchers));
    }

    /**
     * Checks that an element does <strong>not</strong> contain any element matched by the given
     * CSS selector.
     * @param selector the CSS selector
     */
    public static Matcher<Element> hasNoSelector(String selector) {
    	return HasNoSelector.hasNoSelector(selector);
    }

    /**
     * Checks that a collection contains a single element matched against a given matcher.
     *
     * @see DomMatchers#containsInThisOrder(java.util.List)
     * @param elementMatcher the matcher used to validate the {@link org.w3c.dom.Element}
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> withElement(Matcher<? super Element> elementMatcher) {
        return containsInThisOrder(elementMatcher);
    }

    /**
     * Checks that a group of elements are matched in order by a group of matchers.  Each validated
     * element is matched against a single matcher argument, i.e. the first element is matched against
     * the first matcher argument, the second element is matched by second matcher argument, and
     * so on.
     *
     * @see DomMatchers#containsInThisOrder(java.util.List)
     * @param elementMatchers the matchers used to validate the group of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> containsInThisOrder(Matcher<? super Element>... elementMatchers) {
        return containsInThisOrder(Arrays.asList(elementMatchers));
    }

    /**
     * <p>
     * Checks that a group of elements are matched in order by a list of matchers.  Each validated
     * element is matched against a single matcher argument, i.e. the first element is matched against
     * the first element in the matcher list, the second element is matched against the second element in the
     * matcher list, and so on.
     * </p>
     * <p>
     * Note: As of hamcrest 1.3, the <code>hasItems</code> family of matchers return <code>Matcher<Iterable<? extends T>></code>
     * whereas the <code>contains</code> family of matchers return <code>Iterable<Matcher<T>></code>.
     * Unfortunately, this makes them impossible to combine as arguments to
     * {@link DomMatchers#hasSelector(String, org.hamcrest.Matcher)} without this method.
     * <p>
     *
     * @param elementMatchers the list fo matchers used to validate the group of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> containsInThisOrder(List<Matcher<? super Element>> elementMatchers) {
        // Let's force Matcher<Iterable<Element>>, since Element is an interface
        return new IsIterableContainingInOrder(elementMatchers);
    }

    /**
     * Checks that a group of elements are matched in any order by a group of matchers.  Each validated
     * element is matched against a single matcher argument.
     *
     * @see DomMatchers#containsInAnyOrder(org.hamcrest.Matcher[])
     * @param elementMatchers the matchers used to validate the group of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> containsInAnyOrder(Matcher<? super Element>... elementMatchers) {
        return containsInAnyOrder(Arrays.asList(elementMatchers));
    }

    /**
     * <p>
     * Checks that a group of elements are matched in any order by a list of matchers.  Each validated
     * element is matched against a single matcher argument.
     * </p>
     * <p>
     * Note: As of hamcrest 1.3, the <code>hasItems</code> family of matchers return <code>Matcher<Iterable<? extends T>></code>
     * whereas the <code>contains</code> family of matchers return <code>Iterable<Matcher<T>></code>.
     * Unfortunately, this makes them impossible to combine as arguments to
     * {@link DomMatchers#hasSelector(String, org.hamcrest.Matcher)} without this method.
     * <p>
     *
     * @param elementMatchers the list fo matchers used to validate the group of {@link org.w3c.dom.Element}s
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Iterable<Element>> containsInAnyOrder(List<Matcher<? super Element>> elementMatchers) {
        // Let's force Matcher<Iterable<Element>>, since Element is an interface
        return new IsIterableContainingInAnyOrder(elementMatchers);
    }

    /**
     * Checks that a group of elements contains at least one element that is matched by the matcher provided.
     * 
     * @param elementMatcher matcher for the element to look for.
     */
    @SuppressWarnings("unchecked")
	public static Matcher<Iterable<Element>> hasElement(Matcher<? super Element> elementMatcher) {
        return Matchers.hasItems(elementMatcher);
    }

    /**
     * Checks that a group of elements contains, in any order, at least one matched element for
     * each given matcher.
     *
     * @param elementMatchers matchers for elements to look for
     */
    public static Matcher<Iterable<Element>> hasElements(Matcher<? super Element>... elementMatchers) {
        return Matchers.hasItems(elementMatchers);
    }

    /**
     * Checks that an element has the specified tag.
     */
    public static Matcher<Element> withTag(String tagName) {
        return WithTag.withTag(tagName);
    }

    /**
     * Checks that an element content matches exactly a given text.
     */
    public static Matcher<Element> withText(String contentText) {
        return WithContentText.withContent(contentText);
    }
    
    /**
     * Checks that an element content contains only blank characters.
     */
    public static Matcher<Element> withBlankText() {
    	return WithContentText.withBlankContent();
    }

    /**
     * Checks that an element content text is matched by the given matcher.
     */
    public static Matcher<Element> withText(Matcher<? super String> contentMatcher) {
        return WithContentText.withContent(contentMatcher);
    }

    /**
     * Checks that an element has a specified attribute whose value is matched by the given matcher.
     *
     * @param name name of the attribute to validate.
     * @param valueMatcher matcher used to validate the attribute's value.
     */
    public static Matcher<Element> withAttribute(String name, Matcher<? super String> valueMatcher) {
        return WithAttribute.withAttribute(name, valueMatcher);
    }

    /**
     * Checks that an element has the specified attribute with an expected value.
     *
     * @param name the name of the attribute to validate.
     * @param value the expected value of the attribute.
     */
    public static Matcher<Element> withAttribute(String name, String value) {
        return WithAttribute.withAttribute(name, value);
    }

    /**
     * Checks that an element has a given attribute, whatever its value.
     *
     * @param name the name of the expected attribute.
     */
    public static Matcher<Element> withName(String name) {
        return WithAttribute.withName(name);
    }

    /**
     * Checks that an element has an id attribute with the specified value.
     *
     * @param id expected value of the id attribute.
     */
    public static Matcher<Element> withId(String id) {
        return WithAttribute.withId(id);
    }

    /**
     * Checks that an element has a given class. Note that the element can have other classes as well.
     *
     * @param className the class the element should have.
     */
    public static Matcher<Element> withClassName(String className) {
        return WithAttribute.withClassName(className);
    }

    /**
     * Checks that children of an element are matched <strong>in order</strong> by a group of matchers.
     * The number of matchers must be the same as the number of children.
     *
     * Each child will be matched against the matchers at same position, i.e. first child
     * will be matched against the first matcher, second child againt second matcher, and so on.
     */
    public static Matcher<Element> hasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        return HasChildren.hasChildren(childrenMatcher);
    }

    /**
     * Checks that children of an element are matched <strong>in order</strong> by a group of
     * matchers. The number of matchers must be the same as the number of children.
     *
     * Each child will be matched against the matchers at same position, i.e. first child
     * will be matched against the first matcher, second child againt second matcher, and so on.
     */
    public static Matcher<Element> hasChildren(Matcher<Element>... childrenMatchers) {
        return HasChildren.hasChildren(childrenMatchers);
    }

    /**
     * Checks that at least one child of an element is matched by the given matcher.
     */
    @SuppressWarnings("unchecked")
    public static Matcher<Element> hasChild(Matcher<Element> childMatcher) {
        return HasChildren.hasChild(childMatcher);
    }

    /**
     * Checks that a collection of elements is of a certain size.
     *
     * @param size the expected number of elements.
     */
    public static Matcher<Iterable<Element>> withSize(int size) {
        return Matchers.iterableWithSize(size);
    }
}
