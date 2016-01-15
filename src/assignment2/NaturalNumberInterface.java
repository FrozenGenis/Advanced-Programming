package setCalculator;

public interface NaturalNumberInterface<T> extends Data<T> {

    /** ADT for the class NaturalNumberInterface.
     *
     * @author : Leroy Truong & Radu Sibechi
     *
     * @elements : numeric characters of the type Char
     * @structure : linear
     * @domain : length > 0
     *
     * @constructor
     *	NaturalNumber();
     *	    @precondition
     *           -
     *		@postcondition
     *           The new NaturalNumber-object contains '0'.
     **/

    /**
     * @precondition
     * 		c must be a number;
     * @postcondition
     * 	    Existing numeric characters are replaced with c;
     */
    NaturalNumberInterface init(char c);

    /** Returns the number
     * @precondition
     *	    -
     * @postcondition
     *	    The number is returned as type String.
     **/
    String getNumber();

    /** Replaces current number with the parameter number.
     * @precondition
     *	    All characters in the string must be numeric.
     * @postcondition
     *	    Current number is replaced with parameter number.
     **/
    void setNumber(String number);

    /** Adds c to the end of the number.
     * @precondition
     * 		Must be a numeric character.
     * @postcondition
     * 		c is added to the end of the number.
     */
    void addDigit(char c);

    /** Returns the character at the given position.
     * @precondition
     * 		The position must be > 0 AND <= length of the number.
     * @postcondition
     * 		The character at the given position is returned.
     */
    char getDigit(int position);

    /** Returns the length of the number.
     * @precondition
     * 		-
     * @postcondition
     * 		Length of the number is returned.
     */
    int length();

    /** Returns whether the two numbers are equal.
     * @precondition
     * 		-
     * @postcondition
     * 		Returns whether the two numbers are equal.
     */
    boolean equals(NaturalNumberInterface that);

    /** Returns the number as type String
     * @precondition
     *	    -
     * @postcondition
     *	    The number is returned as type String.
     **/
    String toString();

}
