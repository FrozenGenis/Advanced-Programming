package setCalculator;

public class NaturalNumber implements NaturalNumberInterface<NaturalNumber> {

    private StringBuffer number;

    public NaturalNumber() {
        number = new StringBuffer("0");
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
    public boolean equals(NaturalNumberInterface that) {
        return that != null && that.getClass() == this.getClass() && this.compareTo((NaturalNumber)that) == 0;
    }

    @Override
    public int compareTo(NaturalNumber that) {
        return this.getNumber().compareTo(that.getNumber());
    }

    @Override
    public NaturalNumber clone() {
        NaturalNumber result;

        try {
            result = (NaturalNumber)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Could not clone Identifier. This should never happen.");
        }

        return result;
    }

    @Override
    public String toString() {
        return number.toString();
    }

}