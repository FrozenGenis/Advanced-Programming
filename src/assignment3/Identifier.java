package assignment3;

public class Identifier implements IdentifierInterface<Identifier> {

	public static final String CLONE_EXCEPTION = "Could not clone Identifier. This should never happen.";

	public static final String DEFAULT_NAME = "a";

	private StringBuffer name;

	public Identifier() {
		name = new StringBuffer(DEFAULT_NAME);
	}

	public Identifier(char c) {
		name = new StringBuffer();
		name.append(c);
	}

	public Identifier init(char c) {
		name = new StringBuffer();
		name.append(c);
		return this;
	}

	public String getName() {
		return name.toString();
	}

	public void setName(String name) {
		this.name = new StringBuffer(name);
	}

	public char getChar(int position) {
		return name.charAt(position);
	}

	public void addChar(char c) {
		name.append(c);
	}

	public int length() {
		return name.length();
	}

	@Override
	public boolean equals(IdentifierInterface that) {
		return that != null && that.getClass() == this.getClass() && this.compareTo((Identifier) that) == 0;
	}

	@Override
	public int compareTo(Identifier that) {
		return this.getName().compareTo(that.getName());
	}

	@Override
	public Identifier clone() {
		Identifier result;

		try {
			result = (Identifier) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(CLONE_EXCEPTION);
		}

		return result;
	}

	public String toString() {
		return name.toString();
	}

}