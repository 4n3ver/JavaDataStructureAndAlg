import java.util.NoSuchElementException;

/**
 * Recitation #2 1/21/15.
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 */
public class Queue<E> implements QueueADT<E> {

    // Do not add any instance variables
    private LinkedList<E> list;

    public Queue() {
        this.list = new LinkedList<E>();
    }

    @Override
    public void enqueue(E data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }

        list.addToBack(data);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
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
    public void setList(final LinkedList<E> list) {
        this.list = list;
    }
}
