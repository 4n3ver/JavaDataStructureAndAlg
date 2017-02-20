/**
 * MaxHeap implementation
 *
 * @author Felly Rusli
 */
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] arr;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a MaxHeap.
     */
    public MaxHeap() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            size++;
            if (size == arr.length) {
                T[] temp = (T[]) new Comparable [2 * arr.length];
                for (int i = 1; i < size; i++) {
                    temp[i] = arr[i];
                }
                arr = temp;
            }
            arr[size] = item;
            heapify(size, arr);
        }
    }

    /**
     * A helper method for add method to heapify.
     * If the child is greater than the parent, then they will switch.
     * Keep do it (recursion) until its parent is greater.
     * Otherwise, keep going until it hits the root.
     *
     * @param size last index of the heap
     * @param arr heap
     */

    private void heapify(int size, T[] arr) {
        T data = arr[size];
        T parent = arr[(size / 2)];
        if (data != null && parent != null && size > 0
                && data.compareTo(parent) > 0) {
            arr[size / 2] = data;
            arr[size] = parent;
            heapify(size / 2, arr);
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T toRemove = arr[1];
            if (size != 1) {
                arr[1] = arr[size];
                percolate(size, arr, 1);
            }
            arr[size] = null;
            size--;
            return toRemove;
        }
    }

    /**
     * A helper method for remove method, which do percolate down.
     * We check the root and compare it to its children.
     * If the root is less than its child, then swap them.
     * Keep do recursion until parent is greater than both of its children.
     *
     * @param size index of the last heap
     * @param arr heap
     * @param toCheck variable to keep track how far we have gone through heap
     */
    private void percolate(int size, T[] arr, int toCheck) {
        if (toCheck < size) {
            T parent = arr[toCheck];
            T left;
            T right;
            if (2 * toCheck <= size) {
                left = arr[2 * toCheck];
            } else {
                left = null;
            }
            if (2 * toCheck + 1 < size) {
                right = arr[2 * toCheck + 1];
            } else {
                right = null;
            }
            if (left != null && right != null) {
                if (right.compareTo(left) >= 0
                        && right.compareTo(parent) >= 0) {
                    arr[toCheck] = right;
                    arr[2 * toCheck + 1] = parent;
                    toCheck = 2 * toCheck + 1;
                    percolate(size, arr, toCheck);
                } else if (left.compareTo(right) > 0
                        && left.compareTo(parent) > 0) {
                    arr[toCheck] = left;
                    arr[2 * toCheck] = parent;
                    toCheck = 2 * toCheck;
                    percolate(size, arr, toCheck);
                }
            } else if (right == null && left != null
                    && left.compareTo(parent) > 0) {
                arr[toCheck] = left;
                arr[2 * toCheck] = parent;
                toCheck = 2 * toCheck;
                percolate(size, arr, toCheck);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        return arr;
    }
}
