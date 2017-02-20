import java.util.NoSuchElementException;

/**
 * ArrayStack
 * Implementation of a stack using
 * an array as a backing structure
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 * @version 1.0
 */
public class ArrayStack<T> implements StackADT<T> {

    // Do not add instance variables
    private T[] backing;
    private int size;

    /**
     * Construct an ArrayStack with
     * an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public ArrayStack() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct an ArrayStack with the specified
     * initial capacity of initialCapacity
     * @param initialCapacity Initial capacity of the backing array.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        backing = (T[]) new Object[initialCapacity];
        size = 0;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else if (size == backing.length) {
            resize();
        }

        backing[size] = data;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }

        T data = backing[size - 1];
        backing[size - 1] = null;
        size--;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the backing array for your queue.
     * This is for grading purposes only. DO NOT EDIT THIS METHOD.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backing;
    }

    /**
     *  Create new backing array that is double in size.
     */
    private void resize() {
        T[] newBacking = (T[]) new Object[backing.length * 2];
        for (int i = 0; i < backing.length; i++) {
            newBacking[i] = backing[i];
        }
        backing = newBacking;
    }
}
