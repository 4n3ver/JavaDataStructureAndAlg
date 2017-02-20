import java.util.NoSuchElementException;

/**
 * Recitation #2 1/21/15.
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 */
public class Stack<T> implements StackADT<T> {

    // Do not add any instance variables
    private LinkedList<T> list;

    public Stack() {
        this.list = new LinkedList<T>();
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }

        list.addToFront(data);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty stack");
        }

        return list.removeFromFront();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Do not touch - These are for grading purposes
     */
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Do not touch - These are for grading purposes
     */
    public void setList(final LinkedList<T> list) {
        this.list = list;
    }
}
