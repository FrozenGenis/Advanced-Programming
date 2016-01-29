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

	@Override
	public Identifier init(char c) {
		name = new StringBuffer();
		name.append(c);
		return this;
	}

	@Override
	public String getName() {
		return name.toString();
	}

	@Override
	public void setName(String name) {
		this.name = new StringBuffer(name);
	}

	@Override
	public char getChar(int position) {
		return name.charAt(position);
	}

	@Override
	public void addChar(char c) {
		name.append(c);
	}

	@Override
	public int length() {
		return name.length();
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o.getClass() == this.getClass() && this.compareTo((Identifier) o) == 0;
	}

	@Override
	public int compareTo(Identifier that) {
		return this.getName().compareTo(that.getName());
	}

	public String toString() {
		return name.toString();
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

}