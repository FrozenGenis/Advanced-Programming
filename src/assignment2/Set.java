package setCalculator;

public class Set<E extends Data<E>> implements SetInterface<E> {

    List<E> elements;

    public Set() {
        elements = new List<>();
    }

    public Set<E> init() {
        elements.init();
        return this;
    }

    public void add(E element) {
        if (!elements.find(element)) {
            elements.insert(element);
        }
    }

    public void remove(E element) {
        if (elements.find(element)) {
            elements.remove();
        }
    }

    public E get() {
        E result =  elements.retrieve();
        elements.remove();
        return result;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public boolean contains(E element) {
        return elements.find(element);
    }

    @Override
    public Set<E> clone() {
        Set<E> result;

        try {
            //noinspection unchecked
            result = (Set<E>)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Could not clone Set. This should never happen.");
        }

        result.elements = elements.clone();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (elements.isEmpty()) {
            return result.toString();
        }

        E memory = elements.retrieve();
        elements.goToFirst();

        do {
            result.append(elements.retrieve().toString()).append(" ");
        } while (elements.goToNext());

        result.deleteCharAt(result.length() - 1);

        elements.find(memory);

        return result.toString();
    }

    public Set<E> union(Set<E> that) {
        Set<E> result = this.clone();
        Set<E> thatCopy = that.clone();
        thatCopy.elements.goToFirst();

        for (int i = 0; i < that.size(); i++) {
            result.add(thatCopy.get());
        }

        return result;
    }

    public Set<E> intersection(Set<E> that) {
        Set<E> result = new Set<>();
        Set<E> thatCopy = that.clone();
        thatCopy.elements.goToFirst();

        for (int i = 0; i < that.size(); i++) {
            E  element = thatCopy.get();

            if (this.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }

    public Set<E> complement(Set<E> that) {
        Set<E> result = new Set<>();
        Set<E> thatCopy = that.clone();
        thatCopy.elements.goToFirst();

        for (int i = 0; i < that.size(); i++) {
            E element = thatCopy.get();

            if (!this.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }

    public Set<E> symmetricDifference(Set<E> that) {
        Set<E> result = new Set<>();
        Set<E> thisCopy = this.clone();
        Set<E> thatCopy = that.clone();
        thatCopy.elements.goToFirst();

        for (int i = 0; i < that.size(); i++) {
            E element = thatCopy.get();

            if (!this.contains(element)) {
                result.add(element);
            }
        }

        thisCopy.elements.goToFirst();

        for (int i = 0; i < this.size(); i++) {
            E element = thisCopy.get();

            if (!that.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }

}
