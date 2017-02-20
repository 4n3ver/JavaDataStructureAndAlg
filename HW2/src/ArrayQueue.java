import java.util.NoSuchElementException;

/**
 * ArrayQueue
 * Implementation of a queue using
 * an array as the backing structure
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueADT<T> {

    // Do not add instance variables
    private T[] backing;
    private int size;
    private int front;
    private int back;

    /**
     * Construct an ArrayQueue with an
     * initial capacity of INITIAL_CAPACITY
     *
     * Use Constructor Chaining
     */
    public ArrayQueue() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct an ArrayQueue with the specified
     * initial capacity of initialCapacity
     * @param initialCapacity Initial capacity of the backing array.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int initialCapacity) {
        backing = (T[]) new Object[initialCapacity];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else if (size == backing.length) {
            resize();
        }

        backing[back] = data;
        back = (back + 1) % backing.length;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        T data = backing[front];
        backing[front] = null;
        front = (front + 1) % backing.length;
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
            newBacking[i] = backing[(front + i) % backing.length];
        }
        front = 0;
        back = backing.length;
        backing = newBacking;
    }
}
