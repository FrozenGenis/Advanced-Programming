package assignment2;

public class List<E extends Data<E>> implements ListInterface<E> {

	public static final String CLONE_EXCEPTION = "Could not clone List. This should never happen.";

	public static final int INITIAL_AMOUNT_OF_NODES = 0;

	private Node first;
	private Node last;
	private Node current;

	private int numberOfNodes;

	public List() {
		first = last = current = null;
		numberOfNodes = INITIAL_AMOUNT_OF_NODES;
	}

	public boolean isEmpty() {
		return current == null;
	}

	public List<E> init() {
		numberOfNodes = INITIAL_AMOUNT_OF_NODES;
		first = last = current = null;

		return this;
	}

	public int size() {
		return numberOfNodes;
	}

	public List<E> insert(E e) {
		if (numberOfNodes == 0) {
			last = current = first = new Node(e);
		} else if (current == first) {
			if (e.compareTo(current.data) <= 0) {
				insertBeforeCurrent(e);
				first = current;
			} else {
				insertInOrderAsc(e);
			}
		} else if (current == last) {
			if (e.compareTo(current.data) >= 0) {
				insertAfterCurrent(e);
				last = current;
			} else {
				insertInOrderDesc(e);
			}
		} else { // in the middle
			if (e.compareTo(current.data) >= 0) {
				insertInOrderAsc(e);
			} else { // e < current.data
				insertInOrderDesc(e);
			}
		}

		numberOfNodes += 1;
		return this;
	}

	private void insertInOrderAsc(E e) {
		while (true) {
			if (current.data.compareTo(last.data) == 0) {
				if (e.compareTo(current.data) >= 0) {
					insertAfterCurrent(e);
					last = current;
				} else { // e < current
					insertBeforeCurrent(e);
				}

				return;
			}

			if (e.compareTo(current.data) > 0) {
				goToNext();
				continue;
			}

			insertBeforeCurrent(e);
			return;
		}
	}

	private void insertInOrderDesc(E e) {
		while (true) {
			if (current.data.compareTo(first.data) == 0) {
				if (e.compareTo(current.data) <= 0) {
					insertBeforeCurrent(e);
					first = current;
				} else { // e > current
					insertAfterCurrent(e);
				}

				return;
			}

			if (e.compareTo(current.data) < 0) {
				goToPrevious();
				continue;
			}

			insertAfterCurrent(e);
			return;
		}
	}

	private void insertBeforeCurrent(E e) {
		current.prior = new Node(e, current.prior, current);
		current = current.prior;
	}

	private void insertAfterCurrent(E e) {
		current.next = new Node(e, current, current.next);
		current = current.next;
	}

	public E retrieve() {
		return current.data.clone();
	}

	public List<E> remove() {
		if (numberOfNodes == 1) {
			first = last = current = null;
		} else if (current == first) {
			current = first = current.next;
			current.prior = null;
		} else if (current == last) {
			current = last = current.prior;
			current.next = null;
		} else { // in the middle
			current.prior.next = current.next;
			current.next.prior = current.prior;
			current = current.next;
		}

		numberOfNodes -= 1;
		return this;
	}

	public boolean find(E e) {
		if (numberOfNodes == 0) {
			return false;
		}

		current = first;

		while (current != null && e.compareTo(current.data) >= 0) {
			if (e.compareTo(current.data) == 0) {
				return true;
			}
			current = current.next;
		}

		if (current == null) {
			current = last;
		} else {
			current = current.prior;
		}

		return false;
	}

	public boolean goToFirst() {
		if (numberOfNodes == 0) {
			return false;
		}

		current = first;
		return true;
	}

	public boolean goToLast() {
		if (numberOfNodes == 0) {
			return false;
		}

		current = last;
		return true;
	}

	public boolean goToNext() {
		if (numberOfNodes == 0 || current == last) {
			return false;
		}

		current = current.next;
		return true;
	}

	public boolean goToPrevious() {
		if (numberOfNodes == 0 || current == first) {
			return false;
		}

		current = current.prior;
		return true;
	}

	@Override
	public List<E> clone() {
		List<E> result;

		try {
			//noinspection unchecked
			result = (List<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(CLONE_EXCEPTION);
		}

		return result;
	}

	public class Node {

		E data;
		Node prior;
		Node next;

		@SuppressWarnings("unused")
		public Node() {
			this(null, null, null);
		}

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data;
			this.prior = prior;
			this.next = next;
		}

	}

}
