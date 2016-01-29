package assignment3;

import java.util.Iterator;

public interface BinarySearchTreeInterface<E extends Data<E>> extends Clonable<BinarySearchTree<E>> {

	/**
	 * Checks whether the tree is empty.
	 *
	 * @precondition -
	 * @postcondition -  TRUE: The tree is empty.
	 * FALSE: The tree is not empty.
	 */
	boolean isEmpty();

	/**
	 * Returns whether an element is present in the tree.
	 *
	 * @precondition -
	 * @postcondition -  TRUE: The element is present in the tree.
	 * FALSE: The element is not present in the tree.
	 */
	boolean find(E element);

	/**
	 * Adds an element to the tree.
	 *
	 * @precondition -
	 * @postcondition - The element is added to the tree.
	 */
	BinarySearchTree<E> add(E element);

	/**
	 * Removes an element from the tree.
	 *
	 * @precondition - The element is in the tree.
	 * @postcondition - The element is removed from the tree.
	 */
	BinarySearchTree<E> remove(E element);

	/**
	 * Returns the data as an iterator in a monotone non-decreasing order.
	 *
	 * @precondition -
	 * @postcondition - The data in the iterator returned is sorted in a monotone non-decreasing order.
	 */
	Iterator<E> ascendingIterator();

	/**
	 * Returns the data as an iterator in a monotone non-increasing order.
	 *
	 * @precondition -
	 * @postcondition - The data in the iterator returned is sorted in a monotone non-increasing order.
	 */
	Iterator<E> descendingIterator();

}