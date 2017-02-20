import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author  Yoel Ivan on 1/31/2015.
 */
public class DoublyLinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;

    public DoublyLinkedList() {
        head = null;
    }

    public void addToFront(E e) {
        Node<E> newNode = new Node<E>(e, null, head);
        head = newNode;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {
        private Node<E> nextNode;
        private boolean removeOK;

        private MyIterator() {
            nextNode = head;
            removeOK = false;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = nextNode.data;
            nextNode = nextNode.next;
            removeOK = true;
            return data;
        }

        @Override
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            removeOK = false;
            Node<E> currentNode = nextNode.previous;
            Node<E> before = currentNode.previous;
            Node<E> after = currentNode.next;
            if (after != null) {
                after.previous = before;
            } else {
                tail = before;
            }
            if (before != null) {
                before.next = after;
            } else {
                head = after;
            }
        }
    }

    private static class Node<T> {
        private T data;
        private Node next;
        private Node previous;

        private Node(T data, Node previous, Node next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
