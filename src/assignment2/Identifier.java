package assignment2;

public class Identifier implements IdentifierInterface<Identifier> {

	public static final String DEFAULT_NAME = "a";

	private StringBuffer name;

	public Identifier() {
		name = new StringBuffer(DEFAULT_NAME);
	}

	@Override
	public Identifier init(char c) {
		name = new StringBuffer().append(c);
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

	@Override
	public String toString() {
		return name.toString();
	}

	@Override
	public Identifier clone() {
		Identifier result;

		try {
			result = (Identifier) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Could not clone Identifier. This should never happen.");
		}

		result.name = new StringBuffer(name);

		return result;
	}

}