package assignment2;

public class NaturalNumber implements NaturalNumberInterface<NaturalNumber> {

	public static final String CLONE_EXCEPTION = "Could not clone Identifier. This should never happen.";

	public static final String DEFAULT_NUMBER = "0";

	private StringBuffer number;

	public NaturalNumber() {
		number = new StringBuffer(DEFAULT_NUMBER);
	}

	public NaturalNumber init(char c) {
		number = new StringBuffer();
		number.append(c);
		return this;
	}

	public String getNumber() {
		return number.toString();
	}

	public void setNumber(String number) {
		this.number = new StringBuffer(number);
	}

	public void addDigit(char c) {
		number.append(c);
	}

	public char getDigit(int position) {
		return number.charAt(position);
	}

	public int length() {
		return number.length();
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o.getClass() == this.getClass() && this.compareTo((NaturalNumber) o) == 0;
	}

	@Override
	public int compareTo(NaturalNumber that) {
		return Integer.valueOf(this.toString()).compareTo(Integer.valueOf(that.toString()));
	}

	@Override
	public NaturalNumber clone() {
		NaturalNumber result;

		try {
			result = (NaturalNumber) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(CLONE_EXCEPTION);
		}

		return result;
	}

	@Override
	public String toString() {
		return number.toString();
	}

}