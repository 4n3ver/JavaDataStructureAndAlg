import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * These are some simple test cases to get you started with testing your code.
 * Passing these does NOT guarantee any sort of grade
 * Not every method has been tested
 */
public class CircularLinkedListTestStudent {

    private LinkedListInterface<String> list;

    @Before
    public void setup() {
        list = new CircularLinkedList<String>();
    }

    @Test(timeout = 200)
    public void testAdd1() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("1");
        assertEquals(1, list.size());

        list.addToFront("2");
        assertEquals(2, list.size());

        list.addToFront("3");
        assertEquals(3, list.size());

        LinkedListNode<String> node = list.getHead();
        assertEquals("3", node.getData());

        node = node.getNext();
        assertEquals("2", node.getData());

        node = node.getNext();
        assertEquals("1", node.getData());

        //Make sure list is circular
        node = node.getNext();
        assertEquals("3", node.getData());

        //Go backwards
        node = node.getPrevious();
        assertEquals("1", node.getData());

        node = node.getPrevious();
        assertEquals("2", node.getData());
    }

    @Test(timeout = 200)
    public void testAdd2() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToBack("3");
        assertEquals(1, list.size());

        list.addToBack("2");
        assertEquals(2, list.size());

        list.addToBack("1");
        assertEquals(3, list.size());

        LinkedListNode<String> node = list.getHead();
        assertEquals("3", node.getData());

        node = node.getNext();
        assertEquals("2", node.getData());

        node = node.getNext();
        assertEquals("1", node.getData());

        //Make sure list is circular
        node = node.getNext();
        assertEquals("3", node.getData());

        //Go backwards
        node = node.getPrevious();
        assertEquals("1", node.getData());

        node = node.getPrevious();
        assertEquals("2", node.getData());
    }

    @Test(timeout = 200)
    public void testRemove1() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("1");
        assertEquals(1, list.size());

        list.addToFront("2");
        assertEquals(2, list.size());

        list.addToFront("3");
        assertEquals(3, list.size());

        assertEquals("3", list.removeFromFront());
        assertEquals(2, list.size());
        assertEquals("2", list.getHead().getData());
    }

    @Test(timeout = 200)
    public void testRemove2() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("1");
        assertEquals(1, list.size());

        list.addToFront("2");
        assertEquals(2, list.size());

        list.addToFront("3");
        assertEquals(3, list.size());

        assertEquals("1", list.removeFromBack());
        assertEquals(2, list.size());
        assertEquals("2", list.getHead().getPrevious().getData());
    }

    @Test(timeout = 200)
    public void testGet() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("1");
        assertEquals(1, list.size());

        list.addToFront("2");
        assertEquals(2, list.size());

        list.addToFront("3");
        assertEquals(3, list.size());

        assertEquals("3", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("1", list.get(2));
        assertEquals(3, list.size());
    }

    @Test(timeout = 200)
    public void testArray() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("2");
        list.addAtIndex(0, "1");
        list.addToBack("3");
        list.addAtIndex(list.size(), "4");
        list.addToBack("5");

        assertEquals(5, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
        assertEquals("4", list.get(3));
        assertEquals("5", list.get(4));

        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(list.toArray()));
    }

    @Test(timeout = 200)
    public void testRemoval() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addToFront("5");
        list.addAtIndex(0, "3");
        list.addToBack("7");
        list.addAtIndex(list.size(), "8");
        list.addToBack("9");
        list.addAtIndex(2, "6");
        list.addToFront("2");
        list.addAtIndex(0, "1");
        list.addAtIndex(3, "4");

        assertEquals("4", list.removeAtIndex(3));
        assertEquals("9", list.removeFromBack());
        assertEquals("1", list.removeFromFront());
        assertEquals("2", list.removeAtIndex(0));
        assertEquals("8", list.removeAtIndex(list.size()-1));
        assertEquals("7", list.removeFromBack());
        assertEquals("5", list.removeAtIndex(1));
        assertEquals("6", list.removeAtIndex(1));
        assertEquals("3", list.removeFromFront());
        assertEquals("[]", Arrays.toString(list.toArray()));

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.addAtIndex(0, "1");
        assertEquals("1", list.removeFromFront());

        list.addToBack("9");
        assertEquals("9", list.removeAtIndex(0));

        list.addToFront("2");
        assertEquals("2", list.removeFromFront());
    }
}
