// version coded 2015-01-12 (Week 2)
// Now implements Iterable.

import java.util.Iterator;
import java.lang.Iterable;

public class LinkedList implements Iterable<Integer> {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public Iterator<Integer> iterator() {
        return new LinkedListIterator(this);
    }

    private class LinkedListIterator implements Iterator<Integer> {
        private Node current;
        private LinkedListIterator(LinkedList list) {
            current = head;
        }
        public boolean hasNext() {
            return (current != null);
        }
        public Integer next() {
            Node ret = null;
            if (hasNext()) {
                ret = current;
                current = current.next;
                return ret.data;
            } else {
                throw new java.util.NoSuchElementException("No such element");
            }
        }
        public void remove() {
            throw new UnsupportedOperationException("Can't remove");
        }
    }

    public void addToFront(int data) {
        head = new Node(data, head);
    }

    public void addToBack(int data) {
        Node newOne = new Node(data);
        if (head == null) {
            head = newOne;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newOne;
        }
    }

    public String toString() { // uses Node.toString()
        String answer = "";
        Node current = head;
        while (current != null) {
            answer += current + " "; // implicit call here
            current = current.next;
        }
        return answer;
    }

    private class Node {
        private Integer data;
        private Node next;

        private Node(Integer data, Node next) {
            this.data = data;
            this.next = next;
        }

        private Node(Integer data) { // uses constructor chaining
            this(data, null);
        }

        public String toString() {
            return data.toString();
        }
    }

    public LinkedList splitHalf() {
        Node c = head;
        Node mid = head;
        int size = 0;
        LinkedList r = new LinkedList();
        while (c != null) {
            c = c.next;
            if (size % 2 == 0 && size != 0)
                mid = mid.next;
            size++;
        }
        if (mid != null) {
            r.head = mid.next;
            mid.next = null;
        }
        return r;
    }

    public static void main(String[] args) {
        // test add to front and then add to back
        // tests every scenario of toString (implicit calls)
        LinkedList ages = new LinkedList();
        ages.addToFront(42);
        ages.addToFront(20);
        ages.addToBack(100);
        ages.addToBack(60);
        ages.addToBack(75);
        ages.addToBack(75);
        System.out.println("iterator output");
        for (Integer age : ages) {
            System.out.print(age + " ");
        }

        LinkedList half = ages.splitHalf();

        System.out.println("iterator output");
        for (Integer age : ages) {
            System.out.print(age + " ");
        }
        System.out.println();
        for (Integer age : half) {
            System.out.print(age + " ");
        }

    }
}