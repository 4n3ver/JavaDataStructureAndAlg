/**
 * CircularLinkedList implementation
 *
 * @author Yoel Ivan (gtID#:903089980, yivan3@gatech.edu)
 * @version 1.0
 */
public class CircularLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException();
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            addToList(data, curr);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (index == (size - 1)) {
            return head.getPrevious().getData();
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == (size - 1)) {
            return removeFromBack();
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            T remData = curr.getData();
            curr = curr.getNext();
            removePrevious(curr);
            return remData;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (head == null) {
            addToEmptyList(data);
        } else {
            head = addToList(data, head);
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (head == null) {
            addToEmptyList(data);
        } else {
            addToList(data, head);
        }
    }

    @Override
    public T removeFromFront() {
        if (head == null) {
            return null;
        }
        T remData = head.getData();
        if (size == 1) {
            clear();
        } else {
            head = head.getNext();
            removePrevious(head);
        }
        return remData;
    }

    @Override
    public T removeFromBack() {
        if (head == null) {
            return null;
        }
        T remData = head.getPrevious().getData();
        if (size == 1) {
            clear();
        } else {
            removePrevious(head);
        }
        return remData;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        LinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
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
        head = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }


    /**
     * Add a new node to an empty linked list.
     *
     * @param data The data that the new node should hold.
     */
    private void addToEmptyList(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        head = newNode;
        newNode.setNext(newNode);
        newNode.setPrevious(newNode);
        size = 1;
    }

    /**
     * Add a new node to the point of insertion.
     *
     * @param data       The data that the new node should hold.
     * @param pointOfIns Node to indicate the location where the new node
     *                   will be inserted.
     * @return The newly created node.
     */
    private LinkedListNode<T> addToList(T data, LinkedListNode<T> pointOfIns) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data, pointOfIns
                .getPrevious(), pointOfIns);
        pointOfIns.getPrevious().setNext(newNode);
        pointOfIns.setPrevious(newNode);
        size++;
        return newNode;
    }

    /**
     * Remove the node before the <code>pointOfRem</code> node.
     *
     * @param pointOfRem The node after the to be removed node.
     */
    private void removePrevious(LinkedListNode<T> pointOfRem) {
        pointOfRem.setPrevious(pointOfRem.getPrevious().getPrevious());
        pointOfRem.getPrevious().setNext(pointOfRem);
        size--;
    }

}