import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementation of {@link SkipListInterface}
 *
 * @param <T> type of data stored
 * @author Yoel Ivan (yivan3@gatech.edu)
 */
public class SkipList<T extends Comparable<? super T>>
        implements SkipListInterface<T> {
    // Do not add any additional instance variables
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order. When
     * an item is inserted, the flipper is called until it returns a tails. If,
     * for an item, the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        clear();
    }

    /**
     * Check validity of an argument.
     *
     * @param arg object to be validated.
     * @throws IllegalArgumentException if <code>arg</code> is invalid
     */
    private static void checkArg(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("null arg(s)");
        }
    }

    @Override
    public T first() {
        checkEmpty();
        Node<T> probe = head;
        while (probe.getDown() != null) {
            probe = probe.getDown();
        }
        return probe.getNext().getData();
    }

    @Override
    public T last() {
        checkEmpty();
        Node<T> probe = head;
        boolean done = false;
        while (!done) {
            while (probe.getNext() != null) {
                probe = probe.getNext();
            }
            if (probe.getDown() != null) {
                probe = probe.getDown();
            } else {
                done = true;
            }
        }
        return probe.getData();
    }

    @Override
    public void put(T data) {
        checkArg(data);
        Node<T> fetched = fetchNode(data);
        if (fetched.getData() != null && fetched.getData().compareTo(data)
                == 0) {
            fetched.setData(data);
            while (fetched.getDown() != null) {
                fetched = fetched.getDown();
                fetched.setData(data);
            }
        } else {
            Node<T> newNode = new Node<>(data, fetched.getLevel(), fetched,
                    fetched.getNext(), null, null);
            if (fetched.getNext() != null) {
                fetched.getNext().setPrev(newNode);
            }
            fetched.setNext(newNode);
            while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
                fetched = findWayUp(fetched);
                fetched.setNext(new Node<T>(data, fetched.getLevel(), fetched,
                        fetched.getNext(), null, newNode));
                newNode = fetched.getNext();
                newNode.getDown().setUp(newNode);
                if (newNode.getNext() != null) {
                    newNode.getNext().setPrev(newNode);
                }
            }
            size++;
        }
    }

    /**
     * Backtrack to previous level.
     *
     * @param pivot starting node
     * @return node that has a way up to the upper level
     */
    private Node<T> findWayUp(Node<T> pivot) {
        while (pivot.getUp() == null) {
            if (pivot.getData() == null) {
                head = new Node<T>(null, pivot.getLevel() + 1, null, null,
                        null, pivot);
                head.getDown().setUp(head);
                return head;
            }
            pivot = pivot.getPrev();
        }
        return pivot.getUp();
    }

    @Override
    public T remove(T data) {
        checkArg(data);
        Node<T> fetched = fetchNode(data);
        if (fetched.getData() == null || fetched.getData().compareTo(data)
                != 0) {
            throw new NoSuchElementException("not here");
        }
        boolean done = false;
        while (!done) {
            fetched.getPrev().setNext(fetched.getNext());
            if (fetched.getNext() == null) {
                if (fetched.getLevel() > 1 && fetched.getPrev() == head) {
                    head = fetched.getPrev().getDown();
                    head.setUp(null);
                }
            } else {
                fetched.getNext().setPrev(fetched.getPrev());
            }
            if (fetched.getDown() != null) {
                fetched = fetched.getDown();
            } else {
                size--;
                done = true;
            }
        }
        return fetched.getData();
    }

    @Override
    public boolean contains(T data) {
        checkArg(data);
        Node<T> fetched = fetchNode(data);
        return fetched.getData() != null && fetched.getData().equals(data);
    }

    @Override
    public T get(T data) {
        checkArg(data);
        Node<T> fetched = fetchNode(data);
        if (fetched.getData() == null || fetched.getData().compareTo(data)
                != 0) {
            throw new NoSuchElementException("not here");
        }
        return fetched.getData();
    }

    /**
     * Find a node that holds given data or node that act as point of
     * insertion.
     * THIS DOES NOT RETURN THE MOST BOTTOM NODE!!! WILL RETURN HEAD IF EMPTY.
     *
     * @param data data to be found
     * @return {@link Node} that holds max data lower than or equal to
     * <code>data</code> or phantom node if <code>data</code> less than any
     * other data in the list or when the list is empty
     */
    private Node<T> fetchNode(T data) {
        Node<T> probe = head;
        boolean done = false;
        while (!done) {
            if (probe.getNext() != null && (data.compareTo(
                    probe.getNext().getData()) >= 0)) {

                /*
                 * attempt to go all the way to the right until:
                 * > end of level
                 * > next node to be traversed is biggers
                 */
                probe = probe.getNext();
            } else if (probe.getDown() != null && (probe.getData() == null
                    || data.compareTo(probe.getData()) != 0)) {

                /*
                 * attempt to go down one level while:
                 * > not in bottom level
                 * > currently visited node is not equal to data
                 */
                probe = probe.getDown();
            } else {

                /*
                 * > duplicate found
                 * > no duplicate but can no longer go down
                 */
                done = true;
            }
        }
        return probe;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head = new Node<T>(null, 1);
    }

    @Override
    public Set<T> dataSet() {
        Node<T> probe = head;
        while (probe.getDown() != null) {
            probe = probe.getDown();
        }
        Set<T> set = new HashSet<>(size());
        while (probe.getNext() != null) {
            set.add(probe.getNext().getData());
            probe = probe.getNext();
        }
        return set;
    }

    @Override
    public Node<T> getHead() {
        return head;
    }

    /**
     * Check whether the list is empty.
     *
     * @throws NoSuchElementException if the list is empty.
     */
    private void checkEmpty() {
        if (head.getNext() == null) {
            throw new NoSuchElementException("not here!!");
        }
    }
}
