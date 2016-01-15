package setCalculator;

public interface SetInterface<E extends Data<E>> extends Clonable<Set<E>> {

    /**	ADT for the class SetInterface.
     *
     * @author : Leroy Truong & Radu Sibechi
     *
     * @elements : elements of type Data
     * @structure : none
     * @domain : -
     *
     * @constructor
     *	Set();
     *	    @precondition
     *           -
     *		@postcondition
     *           The new Set-object is empty.
     **/

    /** Initializes the Set-object to the empty set.
     * @precondition
     *	    -
     * @postcondition
     *	    The set is empty.
     **/
    SetInterface<E> init();

    /** Adds an element to the set.
     * @precondition
     *	    -
     * @postcondition
     *	    The element is in the set.
     **/
    void add(E element);

    /** Removes an element from the set.
     * @precondition
     *	    -
     * @postcondition
     *	    The element is no longer in the set.
     **/
    void remove(E element);

    /** Returns an element of the set which the current pointer is pointing to and removes it from the set.
     * @precondition
     *	    The set is not empty.
     * @postcondition
     *	    An element is returned and removed from the set.
     **/
    E get();

    /** Returns whether the set is empty.
     * @precondition
     *	    -
     * @postcondition
     *	    TRUE: the number of elements in the set == 0.
     *	    FALSE: the number of elements in the set > 0.
     **/
    boolean isEmpty ();

    /** Returns the size of the set.
     * @precondition
     * 		-
     * @postcondition
     * 		Size of the set is returned.
     */
    int size();

    /** Returns whether element is in the set.
     * @precondition
     *	    -
     * @postcondition
     *	    TRUE: the element is in the set.
     *	    FALSE: the element is not in the set.
     **/
    boolean contains(E element);

    /** Returns a string containing all the elements of the list separated by ','.
     * @precondition
     *      -
     * @postcondition
     * 		A string of all the elements of the list is returned.
     */
    String toString();

    /** Returns a new Set-object containing all the distinct elements from the set and the parameter.
     * @precondition
     *      -
     * @postcondition
     * 		A new Set-object is returned containing all the distinct elements from the set and the parameter.
     */
    Set<E> union(Set<E> that);

    /** Returns a new Set-object containing all the elements which are present in both the set and parameter.
     * @precondition
     *      -
     * @postcondition
     * 		A new Set-object is returned containing all the elements which are present in both the set and parameter.
     */
    Set<E> intersection(Set<E> that);

    /** Returns a new Set-object containing all the elements which are present in the parameter but absent in the set.
     * @precondition
     *      -
     * @postcondition
     * 		A new Set-object is returned containing all the elements which are present in the parameter but absent in the set.
     */
    Set<E> complement(Set<E> that);

    /** Returns a new Set-object containing all elements which are present in the set but not in the parameter
     *  and elements which are present in the parameter but not in the set.
     * @precondition
     *      -
     * @postcondition
     * 		A new Set-object is returned containing all elements which are present in the set but not in the parameter
     *      and elements which are present in the parameter but not in the set.
     */
    Set<E> symmetricDifference(Set<E> that);

}
