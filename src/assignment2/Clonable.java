package setCalculator;

public interface Clonable<E> extends Cloneable {

    /* In order to be able to use clone() everywhere it is overridden with
       a public version.
    */

    E clone();

}
