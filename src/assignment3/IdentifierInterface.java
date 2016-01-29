package assignment3;

public interface IdentifierInterface<E> extends Data<E> {

	/** ADT for the class IdentifierInterface.
	 *
	 * @author : Leroy Truong & Radu Sibechi
	 *
	 * @elements :  alphanumeric characters of the type Char
	 * @structure : linear
	 * @domain :    first character is a letter AND length > 0
	 *
	 * @constructor
	 *	Identifier();
	 *	    @precondition
	 *           -
	 *		@postcondition
	 *           The new Identifier-object contains 'a'.
	 * @constructor
	 *	Identifier(char c);
	 *	    @precondition
	 *           -
	 *		@postcondition
	 *           The new Identifier-object contains char c.
	 **/

	/**
	 * @precondition    Parameter c must be a letter.
	 * @postcondition   Existing characters replaced with parameter c.
	 */
	IdentifierInterface init(char c);

	/**
	 * Returns the value of the element
	 *
	 * @precondition    -
	 * @postcondition   The identifier is returned as a String.
	 **/
	String getName();

	/**
	 * Replaces the identifier with name.
	 *
	 * @precondition    The first character of name must be a letter AND length > 0 AND all characters must be alphanumeric.
	 * @postcondition   The identifier is replaced with name.
	 **/
	void setName(String name);

	/**
	 * Adds parameter c to the end of the Identifier.
	 *
	 * @precondition    Parameter c must be an alphanumeric character.
	 * @postcondition   Parameter c is added to the end of the Identifier.
	 */
	void addChar(char c);

	/**
	 * Returns the character at the given position.
	 *
	 * @precondition    The position must be > 0 AND <= length of the Identifier.
	 * @postcondition   The character at the given position is returned.
	 */
	char getChar(int position);

	/**
	 * Returns the length of the identifier.
	 *
	 * @precondition    -
	 * @postcondition   -Length of the identifier is returned.
	 */
	int length();

	/**
	 * Returns whether the given object is equal to this identifier.
	 *
	 * @precondition    -
	 * @postcondition   Returns whether the given object is equal to this identifier.
	 */
	boolean equals(Object o);

	/**
	 * Returns the string value of the identifier
	 *
	 * @precondition    The specified identifier must not be null.
	 * @postcondition   Returns the string value of the identifier.
	 */
	String toString();

}