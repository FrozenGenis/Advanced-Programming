package assignment2;

public class NaturalNumber implements NaturalNumberInterface<NaturalNumber> {

	public static final String CLONE_EXCEPTION = "Could not clone Identifier. This should never happen.";

	public static final String DEFAULT_NUMBER = "0";

	private StringBuffer number;

	public NaturalNumber() {
		number = new StringBuffer(DEFAULT_NUMBER);
	}

	@Override
	public NaturalNumber init(char c) {
		number = new StringBuffer();
		number.append(c);
		return this;
	}

	@Override
	public String getNumber() {
		return number.toString();
	}

	@Override
	public void setNumber(String number) {
		this.number = new StringBuffer(number);
	}

	@Override
	public void addDigit(char c) {
		number.append(c);
	}

	@Override
	public char getDigit(int position) {
		return number.charAt(position);
	}

	@Override
	public int length() {
		return number.length();
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o.getClass() == this.getClass() && this.compareTo((NaturalNumber) o) == 0;
	}

	@Override
	public int compareTo(NaturalNumber that) {
		if (this.length() > that.length()) {
			return 1;
		} else if (this.length() < that.length()) {
			return -1;
		} // else equal length

		for (int i = 0; i < this.length(); i++) {
			Character thisDigit = this.getDigit(i);
			Character thatDigit = that.getDigit(i);
			int comparison = thisDigit.compareTo(thatDigit);

			if (comparison != 0) {
				return comparison;
			}
		}

		return 0;
	}

	@Override
	public String toString() {
		return number.toString();
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

}