package assignment2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {

	@Before
	public void setUp() {
		// Add any maintenance which is necessary to set up your tests.
	}

	@Test
	public void testIsEmpty() {
		// Test an empty list.
		List<Letter> list = new List<>();
		assertTrue("New list should be empty", list.isEmpty());

		list.insert(new Letter('a'));
		assertFalse("Adding one element should return false.", list.isEmpty());

		list.remove();
		assertTrue("Removing should make list empty again.", list.isEmpty());
	}

	@Test
	public void testInit() {
		List<Letter> list = new List<>();

		// Create an empty list with init.
		list.init();
		assertTrue("Init on empty list should return an empty list", list.isEmpty());

		// Add item, init should still be empty.
		list.insert(new Letter('a'));
		list.init();
		assertTrue("Init on non-empty list should return an empty list", list.isEmpty());
	}

	@Test
	public void testSize() {
		List<Letter> list = new List<>();

		assertEquals("Empty list should be size 0", 0, list.size());

		// Insert one item
		list.insert(new Letter('a'));
		assertEquals("List of one element should have size 1", 1, list.size());

		// Add 200 items to the list.
		for (int i = 0; i < 200; i++) {
			list.insert(new Letter('a'));
		}
		assertEquals("Adding many elements should result in a long list", 201, list.size());

		// Remove 1 item -> 200 items left
		list.remove();
		assertEquals("Removing one item should decrement the size", 200, list.size());

		// Init should empty the list.
		list.init();
		assertEquals("Init should set size to zero", 0, list.size());
	}

	@Test
	public void testInsert() {
		List<Letter> list = new List<>();

		// Insert first item
		// Inserting into empty list is an edge case.
		Letter c = new Letter('c');
		list.insert(c);
		assertEquals("Inserted element should be in list", c, list.retrieve());

		// Append second item
		Letter f = new Letter('f');
		list.insert(f);

		list.goToLast();
		assertEquals("Insert should order larger elements later in list", f, list.retrieve());

		list.goToFirst();
		assertEquals("Previous elements should still be in list", c, list.retrieve());


		// Insert in front of list
		// Test that the list is sorted correctly when inserting a smaller item.
		Letter a = new Letter('a');
		list.insert(a);
		list.goToFirst();
		assertEquals("Insert should order smaller elements earlier in list", a, list.retrieve());


		// Insert at the end.
		// Test that the list is sorted correctly when inserting a larger item.
		Letter k = new Letter('k');
		list.insert(k);
		list.goToLast();
		assertEquals(k, list.retrieve());

		// Insert between two items.
		// The order has to be preserved when inserting an item between existing items.
		Letter b = new Letter('b');
		list.insert(b);
		list.goToFirst();
		assertEquals(a, list.retrieve());
		list.goToNext();
		assertEquals(b, list.retrieve());
		list.goToNext();
		assertEquals(c, list.retrieve());
		list.goToNext();
		assertEquals(f, list.retrieve());
		list.goToNext();
		assertEquals(k, list.retrieve());

		// Insert a duplicate item.
		// The order has to be preserved when inserting a duplicate item.
		Letter duplicate = new Letter('b');
		list.insert(duplicate);
		list.goToFirst();
		assertEquals(a, list.retrieve());
		list.goToNext();
		assertEquals(b, list.retrieve());
		list.goToNext();
		assertEquals(b, list.retrieve());
		list.goToNext();
		assertEquals(c, list.retrieve());
		list.goToNext();
		assertEquals(f, list.retrieve());
		list.goToNext();
		assertEquals(k, list.retrieve());

		// Number test
		NaturalNumber n1 = new NaturalNumber();
		n1.setNumber("1");
		NaturalNumber n2 = new NaturalNumber();
		n2.setNumber("2");
		NaturalNumber n3 = new NaturalNumber();
		n3.setNumber("3");
		NaturalNumber n4 = new NaturalNumber();
		n4.setNumber("4");
		NaturalNumber n5 = new NaturalNumber();
		n5.setNumber("5");
		NaturalNumber n6 = new NaturalNumber();
		n6.setNumber("6");
		NaturalNumber n7 = new NaturalNumber();
		n7.setNumber("7");
		NaturalNumber n8 = new NaturalNumber();
		n8.setNumber("8");
		NaturalNumber n9 = new NaturalNumber();
		n9.setNumber("9");
		NaturalNumber n10 = new NaturalNumber();
		n10.setNumber("10");

		List<NaturalNumber> numberList = new List<>();
		numberList.insert(n5);
		numberList.insert(n2);
		numberList.insert(n8);
		numberList.insert(n1);
		numberList.insert(n7);
		numberList.insert(n4);
		numberList.insert(n10);
		numberList.insert(n3);
		numberList.insert(n6);
		numberList.insert(n9);

		numberList.goToFirst();
		assertEquals(n1, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n2, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n3, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n4, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n5, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n6, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n7, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n8, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n9, numberList.retrieve());
		numberList.goToNext();
		assertEquals(n10, numberList.retrieve());
	}

	@Test
	public void testRetrieve() {
		List<Letter> list = new List<>();

		Letter z = new Letter('z');
		list.insert(z);

		// Retrieve should return a copy.
		Letter letter = list.retrieve();
		assertFalse("Retrieve should return another object", z == letter);
		assertEquals("Retrieve should return an equal object", z, letter);
	}

	@Test
	public void testRemove() {
		List<Letter> list = new List<>();
		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		list.insert(a);
		list.insert(b);
		list.insert(c);
		list.insert(d);
		// Remove an element in the middle
		list.goToFirst();
		list.goToNext();
		list.remove();

		assertEquals(c, list.retrieve());

		// Remove last element in list
		list.goToLast();
		list.remove();
		assertEquals(c, list.retrieve());


		// Remove on list with size 1
		list.remove();
		list.remove();
		try {
			assertNull(list.retrieve()); // Inconsistent specification. Undefined behaviour for retrieve on empty list.
		} catch (NullPointerException ignored) {
		}
	}

	@Test
	public void testFind() {
		List<Letter> list = new List<>();

		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		Letter e = new Letter('e');
		list.insert(a);
		list.insert(c);
		list.insert(e);

		assertEquals(true, list.find(a));

		assertFalse(list.find(b));
		assertEquals(a, list.retrieve());

		assertEquals(true, list.find(c));

		assertFalse(list.find(d));
		assertEquals(c, list.retrieve());

		assertEquals(true, list.find(e));
	}

	@Test
	public void testGoToFirst() {
		List<Letter> list = new List<>();

		// Test on empty list
		assertFalse(list.goToFirst());

		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		list.insert(a);
		list.insert(b);
		list.insert(c);
		list.insert(d);

		// Test on list with some elements.
		assertTrue(list.goToFirst());

		assertEquals(a, list.retrieve());
	}

	@Test
	public void testGoToLast() {
		List<Letter> list = new List<>();

		// Test on empty list
		assertFalse(list.goToLast());

		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		list.insert(a);
		list.insert(b);
		list.insert(c);
		list.insert(d);

		// Test on list with some elements.
		assertTrue(list.goToLast());
		assertEquals(d, list.retrieve());
	}

	@Test
	public void testGoToNext() {
		List<Letter> list = new List<>();

		// Test on empty list
		assertFalse(list.goToNext());

		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		list.insert(a);
		list.insert(b);
		list.insert(c);
		list.insert(d);

		// Test on last element
		assertFalse(list.goToNext());
		assertEquals(d, list.retrieve());


		// Test on first
		list.goToFirst();
		assertTrue(list.goToNext());
		assertEquals(b, list.retrieve());
	}

	@Test
	public void testGoToPrevious() {
		List<Letter> list = new List<>();

		// Test on empty list
		assertFalse(list.goToNext());

		Letter a = new Letter('a');
		Letter b = new Letter('b');
		Letter c = new Letter('c');
		Letter d = new Letter('d');
		list.insert(a);
		list.insert(b);
		list.insert(c);
		list.insert(d);

		// Test on last element
		list.goToLast();
		assertTrue(list.goToPrevious());
		assertEquals(c, list.retrieve());

		// Test on first
		list.goToFirst();
		assertFalse(list.goToPrevious());
		assertEquals(a, list.retrieve());
	}


	/**
	 * Represents a comparable and clonable Letter.
	 * <p>
	 * This internal class is only used for testing.
	 * It is independent of any of your Implementations.
	 * <p>
	 * If you write your own tests you may also use your own
	 * Implementations (i.e., of Identifier).
	 */
	private class Letter implements Data<Letter> {

		private char letter;

		public Letter(char c) {
			this.letter = c;
		}

		@SuppressWarnings("NullableProblems")
		public int compareTo(Letter l) {
			return this.letter - l.letter;
		}

		@SuppressWarnings("CloneDoesntCallSuperClone")
		public Letter clone() {
			return new Letter(this.letter);
		}

		public boolean equals(Object o) {
			return o != null && o.getClass() == this.getClass() && this.compareTo((Letter) o) == 0;
		}
	}

}