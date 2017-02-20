import java.util.NoSuchElementException;

/**
 * {@link MaxHeap} is an Binary Tree like ADT where the parent element is
 * always bigger than the child's element.
 *
 * @param <T> generic type
 * @author Yoel Ivan (yivan3@gatech.edu)
 */
public class MaxHeap<T extends Comparable<? super T>>
        implements HeapInterface<T> {

    private T[] arr;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a MaxHeap.
     */
    public MaxHeap() {
        clear();
    }

    /**
     * <code>src.length</code> has to be at least <code>size + 1</code>
     * BUILD HEAP
     *
     * @param src
     * @param size
     */
    public MaxHeap(T[] src, int size) {
        arr = src;
        arr[size] = arr[0];
        arr[0] = null;
        this.size = size + 1;
        heapify();
    }

    /**
     * In place heap sort on the data. Due to implementation, element 0 will be
     * <code>null</code>.
     */
    public void sort() {
        while (size() > 0) {
            T temp = remove();
            arr[size] = temp;
        }
    }

    /**
     * TOTAL HEAPIFY!!!!!!!!!!!!!!!!!!!!!!!! AAAAAAAAAAAAAAAAAAAAAA.
     */
    private void heapify() {
        for (int i = size() / 2; i > 0; i--) {
            heapDown(i);
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        checkCapacity(size + 1);
        arr[size++] = item;
        heapUp();
    }

    /**
     * This method called upon adding a new element to keep the property of the
     * heap.
     */
    private void heapUp() {
        int child = size - 1;   // always begin with recently added element
        while (child > 1 && arr[child].compareTo(arr[parentOf(child)]) > 0) {
            swap(child, parentOf(child));
            child = parentOf(child);
        }
    }

    /**
     * Calculate parent index based on passed child index.
     *
     * @param childIndex index of the child
     * @return index of parent of the child
     */
    private int parentOf(int childIndex) {
        return childIndex / 2;
    }

    /**
     * Calculate index of left child of given index.
     *
     * @param parentIndex index of the parent
     * @return left child index of the parent
     */
    private int leftChildOf(int parentIndex) {
        return 2 * parentIndex;
    }

    /**
     * Calculate index of right child of given index.
     *
     * @param parentIndex index of the parent
     * @return right child index of the parent
     */
    private int rightChildOf(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    /**
     * Check whether the parent has a left child.
     *
     * @param parentIndex index of the parent
     * @return <code>true</code> if the parent has left child,
     * <code>false</code> otherwise
     */
    private boolean hasLeftChild(int parentIndex) {
        return leftChildOf(parentIndex) < size;
    }

    /**
     * Check whether the parent has a right child.
     *
     * @param parentIndex index of the parent
     * @return <code>true</code> if the parent has right child,
     * <code>false</code> otherwise
     */
    private boolean hasRightChild(int parentIndex) {
        return rightChildOf(parentIndex) < size;
    }

    /**
     * Check whether the parent has two child.
     *
     * @param parentIndex index of the parent
     * @return <code>true</code> if the parent has right child,
     * <code>false</code> otherwise
     */
    private boolean hasTwoChild(int parentIndex) {
        return hasLeftChild(parentIndex) && hasRightChild(parentIndex);
    }

    /**
     * This method called upon removal of the max element to keep the property
     * of the heap.
     */
    private void heapDown(int i) {
        int parent = i;
        boolean done = false;
        while (!done) {
            if (hasTwoChild(parent) && arr[rightChildOf(parent)].compareTo(
                    arr[leftChildOf(parent)]) >= 0 && arr[rightChildOf(parent)]
                    .compareTo(arr[parent]) > 0) {

                /*
                 * SWAP WITH RIGHT CHILD
                 * if the parent has 2 child and right child is greater than
                 * both parent and (can be equal to) left child
                 */
                swap(rightChildOf(parent), parent);
                parent = rightChildOf(parent);
            } else if ((hasTwoChild(parent) && arr[leftChildOf(parent)]
                    .compareTo(arr[rightChildOf(parent)]) > 0
                    && arr[leftChildOf(parent)].compareTo(arr[parent]) > 0)
                    || (hasLeftChild(parent) && !hasRightChild(parent)
                            && arr[leftChildOf(parent)].compareTo(arr[parent])
                            > 0)) {

                /*
                 * SWAP WITH LEFT CHILD
                 * if the parent has two child, and the left child is greater
                 * than both parent and right child OR parent only have 1 child
                 * (which must be a left child) and that child is greater than
                 * the parent
                 */
                swap(leftChildOf(parent), parent);
                parent = leftChildOf(parent);
            } else {
                done = true;
            }
        }
    }

    /**
     * Swap the value of 2 element in the tree.
     *
     * @param i index of the 1st element
     * @param j index of the 2nd element
     */
    private void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Check whether the backing data structure able to maintain
     * <code>newCap</code> capacity and resize the backing data structure if the
     * <code>newCap</code> cannot be currently handled.
     *
     * @param newCap new capacity to be checked.
     */
    private void checkCapacity(int newCap) {
        if (newCap > arr.length) {
            T[] newBack = (T[]) new Comparable[arr.length * 2];
            for (int i = 1; i < arr.length; i++) {
                newBack[i] = arr[i];
            }
            arr = newBack;
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty heap");
        }
        T temp = arr[1];
        arr[1] = null;
        swap(1, --size);
        heapDown(1);
        return temp;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size - 1;
    }

    @Override
    public void clear() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 1;
    }

    @Override
    public Comparable[] getBackingArray() {
        return arr;
    }
}