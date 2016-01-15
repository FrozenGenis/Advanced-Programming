package setCalculator;

public class Identifier implements IdentifierInterface<Identifier> {
	
	private StringBuffer name;
	
	public Identifier() {
    	name = new StringBuffer("a");
    }

    public Identifier init(char c) {
        name = new StringBuffer().append(c);
        return this;
    }

    public String getName(){
        return name.toString();
    }

    public void setName(String name){
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
        return that != null && that.getClass() == this.getClass() && this.compareTo((Identifier)that) == 0;
    }

    @Override
    public int compareTo(Identifier that) {
        return this.getName().compareTo(that.getName());
    }

    @Override
    public Identifier clone() {
        Identifier result;

        try {
            result = (Identifier)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Could not clone Identifier. This should never happen.");
        }

        result.name = new StringBuffer(name);

        return result;
    }

    @Override
    public String toString() {
        return name.toString();
    }

}