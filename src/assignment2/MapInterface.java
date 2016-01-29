package assignment2;

public interface MapInterface<K extends Data<K>, V extends Clonable<V>> {

	/** ADT for the class MapInterface.
	 *
	 * @author : Leroy Truong & Radu Sibechi
	 *
	 * @elements :  K:V pairs of elements of type Data and Clonable respectively.
	 * @structure : none
	 * @domain :    -
	 *
	 * @constructor
	 *	Map();
	 *	    @precondition
	 *           -
	 *		@postcondition
	 *           The new Map-object is empty.
	 **/

	/**
	 * Initializes the Map-object to the empty map.
	 *
	 * @precondition    -
	 * @postcondition   The map is empty.
	 **/
	MapInterface<K, V> init();

	/**
	 * Puts a key:value pair into the map.
	 *
	 * @precondition    -
	 * @postcondition   The element is in the map.
	 **/
	void put(K key, V value);

	/**
	 * Removes the specified key as well as its corresponding value from the map.
	 *
	 * @precondition    The map must contain the key.
	 * @postcondition   The key and its corresponding value are no longer in the map.
	 **/
	void remove(K key);

	/**
	 * Returns a copy the value corresponding to the specified key.
	 *
	 * @precondition    The key must be in the map.
	 * @postcondition   A copy of the value is returned.
	 **/
	V get(K key);

	/**
	 * Returns whether the map is empty.
	 *
	 * @precondition
	 * @postcondition   TRUE: the number of key:value pairs in the map == 0.
	 *                  FALSE: the number of key:value pairs in the map > 0.
	 **/
	boolean isEmpty();

	/**
	 * Returns the size of the map.
	 *
	 * @precondition    -
	 * @postcondition   Size of the map is returned.
	 */
	int size();

	/**
	 * Returns whether the specified key is in the map.
	 *
	 * @precondition    -
	 * @postcondition   TRUE: the key is in the map.
	 *                  FALSE: the key is not in the map.
	 **/
	boolean contains(K key);

}
