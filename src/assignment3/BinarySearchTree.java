package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree<E extends Data<E>> implements BinarySearchTreeInterface<E> {

	public static final String CLONE_EXCEPTION = "Could not clone BinarySearchTree. This should never happen.";

	private Node root;

	public BinarySearchTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean find(E element) {
		Node current = root;

		while (current != null) {
			if (current.data.compareTo(element) == 0) {
				return true;
			}

			if (current.data.compareTo(element) > 0) {
				current = current.left;
			} else { // current.data < 0
				current = current.right;
			}
		}

		return false;
	}

	public BinarySearchTree<E> add(E element) {
		root = add(root, element);
		return this;
	}

	public Node add(Node node, E element) {
		if (node == null) {
			return new Node(element);
		}

		if (element.compareTo(node.data) <= 0) {
			node.left = add(node.left, element);
		} else { // element > node.data
			node.right = add(node.right, element);
		}

		return node;
	}

	public BinarySearchTree<E> remove(E element) {
		root = remove(root, element);
		return this;
	}

	public Node remove(Node node, E element) {
		if (element.compareTo(node.data) < 0) {
			node.left = remove(node.left, element);
		} else if (element.compareTo(node.data) > 0) {
			node.right = remove(node.right, element);
		} else if (node.left != null && node.right != null) {
			node.data = findMaximum(node.left);
			node.left = remove(node.left, node.data);
		} else {
			node = node.left != null ? node.left : node.right;
		}

		return node;
	}

	private E findMaximum(Node node) {
		if (node.right == null) {
			return node.data;
		} else {
			return findMaximum(node.right);
		}
	}

	public Iterator<E> ascendingIterator() {
		List<E> result = new ArrayList<>();

		populateAscendingArrayList(root, result);

		return result.iterator();
	}

	public Iterator<E> descendingIterator() {
		List<E> result = new ArrayList<>();

		populateDescendingArrayList(root, result);

		return result.iterator();
	}

	private void populateAscendingArrayList(Node node, List<E> list) {
		if (node != null) {
			populateAscendingArrayList(node.left, list);
			list.add(node.data);
			populateAscendingArrayList(node.right, list);
		}
	}

	private void populateDescendingArrayList(Node node, List<E> list) {
		if (node != null) {
			populateDescendingArrayList(node.right, list);
			list.add(node.data);
			populateDescendingArrayList(node.left, list);
		}
	}

	public BinarySearchTree<E> clone() {
		BinarySearchTree<E> result;

		try {
			//noinspection unchecked
			result = (BinarySearchTree<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(CLONE_EXCEPTION);
		}

		result.root = root == null ? null : root.clone();

		return result;
	}

	private class Node {

		public static final String CLONE_EXCEPTION = "Could not clone Node. This should never happen ";

		private E data;
		private Node left;
		private Node right;

		@SuppressWarnings("unused")
		public Node() {
			this.data = null;
			this.left = null;
			this.right = null;
		}

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		public Node clone() {
			Node result;

			try {
				//noinspection unchecked
				result = (Node) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new Error(CLONE_EXCEPTION);
			}

			result.data = data == null ? null : data.clone();
			result.left = left.clone();
			result.right = right.clone();

			return result;
		}

	}

}