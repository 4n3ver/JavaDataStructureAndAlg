/**
 * Created by Yoel Ivan on 1/15/2015.
 */
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * USE AT YOUR OWN RISK
 * @author: Jessica Rosenfield
 */
public class jrosenfield3JUNIT {
    LinkedListInterface<String> list;

    @Before
    public void setUp() {
        list = new CircularLinkedList<String>();
    }

    @Test(timeout = 250)
    public void testAddAtIndex() {
        assertEquals(0, list.size());
        list.addAtIndex(0, "5");
        assertEquals(1, list.size());

        list.addAtIndex(0, "4");
        list.addAtIndex(0, "3");
        list.addAtIndex(0, "2");
        list.addAtIndex(0, "1");
        assertEquals(5, list.size());
        assertArrayEquals(new String[]{"1", "2", "3", "4", "5"}, list.toArray());

        list.addAtIndex(2, "100");
        assertEquals(6, list.size());
        assertArrayEquals(new String[]{"1", "2", "100", "3", "4", "5"},
                list.toArray());

        list.addAtIndex(6, "6");
        assertEquals(7, list.size());
        assertArrayEquals(new String[]{"1", "2", "100", "3", "4", "5", "6"},
                list.toArray());
    }

    @Test(timeout = 250, expected = IllegalArgumentException.class)
    public void testAddAtIndexAddNull() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = 250, expected = IndexOutOfBoundsException.class)
    public void getTestAddAtIndexOutOfBounds1() {
        list.addAtIndex(-5, "1");
    }

    @Test(timeout = 250, expected = IndexOutOfBoundsException.class)
    public void getTestAddAtIndexOutOfBounds2() {
        list.addAtIndex(1, "1");
    }

    @Test(timeout = 250)
    public void testGet() {
        list.addAtIndex(0, "4");
        list.addAtIndex(0, "3");
        list.addAtIndex(0, "2");
        list.addAtIndex(0, "1");
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("4", list.get(3));
    }

    @Test(timeout = 250, expected = IndexOutOfBoundsException.class)
    public void testGetException1() {
        list.addAtIndex(0, "4");
        list.addAtIndex(0, "3");
        list.addAtIndex(0, "2");
        list.addAtIndex(0, "1");
        list.get(4);
    }

    @Test(timeout = 250, expected = IndexOutOfBoundsException.class)
    public void testGetException2() {
        list.get(0);
    }

    @Test(timeout = 250)
    public void testRemoveAtIndex() {
        list.addAtIndex(0, "1");
        list.removeAtIndex(0);
        assertEquals(0, list.size());
        assertEquals(null, list.getHead());

        list.addAtIndex(0, "4");
        list.addAtIndex(0, "3");
        list.addAtIndex(0, "2");
        list.addAtIndex(0, "1");
        list.removeAtIndex(2);
        assertEquals(3, list.size());
        assertArrayEquals(new String[]{"1", "2", "4"}, list.toArray());

        list.removeAtIndex(0);
        assertEquals("2", list.get(0));
        assertEquals("2", list.getHead().getData());
    }

    @Test(timeout = 250, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexError1() {
        list.removeAtIndex(0);
    }

    @Test(timeout = 250)
    public void testAddToFront() {
        list.addToFront("1");
        assertEquals("1", list.get(0));
        assertEquals("1", list.getHead().getData());

        list.addToFront("0");
        assertEquals(2, list.size());
        assertEquals("0", list.get(0));
        assertEquals("0", list.getHead().getData());
    }

    @Test(timeout = 250, expected = IllegalArgumentException.class)
    public void testAddToFrontError() {
        list.addToFront(null);
    }

    @Test(timeout = 250)
    public void testAddToBack() {
        list.addToBack("1");
        assertEquals(1, list.size());
        assertEquals("1", list.get(0));
        assertEquals("1", list.getHead().getData());

        list.addToBack("2");
        assertEquals(2, list.size());
        assertArrayEquals(new String[]{"1", "2"}, list.toArray());
        assertEquals("1", list.getHead().getData());
    }

    @Test(timeout = 250, expected = IllegalArgumentException.class)
    public void testAddToBackError() {
        list.addToBack(null);
    }

    @Test(timeout = 250)
    public void testRemoveFromFront() {
        assertNull(list.removeFromFront());

        list.addToFront("1");
        assertEquals("1", list.removeFromFront());
        assertEquals(0, list.size());
        assertNull(list.getHead());
    }

    @Test(timeout = 250)
    public void testRemoveFromBack() {
        assertNull(list.removeFromBack());

        list.addToFront("1");
        assertEquals("1", list.removeFromBack());
        assertEquals(0, list.size());
        assertNull(list.getHead());

        list.addToFront("2");
        list.addToFront("1");
        assertEquals("2", list.removeFromBack());
        assertEquals("1", list.getHead().getData());
    }

    @Test(timeout = 250)
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testClear() {
        list.addAtIndex(0, "4");
        list.addAtIndex(0, "3");
        list.addAtIndex(0, "2");
        list.addAtIndex(0, "1");
        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.removeFromFront());
        assertNull(list.removeFromBack());
    }
}
