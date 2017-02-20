import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ndandekar8JUNIT {
    private CircularLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new CircularLinkedList<>();
    }

    @After
    public void tearDown() {
        list = null;
    }

    @Test
    public void testCircularLinkedOneNode() {
        list.addToBack(1);

        LinkedListNode<Integer> head = list.getHead();

        Assert.assertEquals("previous not equal to current", head, head.getPrevious());
        Assert.assertEquals("next not equal to current", head, head.getNext());
    }

    @Test
    public void testCircularLinkedTwoNodes() {
        list.addToBack(1);
        list.addToBack(2);

        LinkedListNode<Integer> head = list.getHead();
        LinkedListNode<Integer> nonHead = head.getNext();

        Assert.assertEquals("previous not equal to next", head.getPrevious(), head.getNext());
        Assert.assertEquals("previous not equal to next", nonHead.getPrevious(), nonHead.getNext());
    }

    @Test
    public void testCircularLinkedMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        int i;
        LinkedListNode<Integer> current;

        for (i = 0, current = list.getHead(); i < list.size(); i++, current = current.getNext()) {
            LinkedListNode<Integer> prev = current.getPrevious();
            LinkedListNode<Integer> next = current.getNext();

            Assert.assertEquals("previous's next not equal to current", prev.getNext(), current);
            Assert.assertEquals("next's previous not equal to current", next.getPrevious(), current);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexUnderBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(-1, 0);
    }

    @Test
    public void testAddAtIndexZero() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(0, 0);

        Object[] array = {0, 1, 2, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddAtIndexMidArray() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(2, 0);

        Object[] array = {1, 2, 0, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddAtIndexBeforeEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(4, 0);

        Object[] array = {1, 2, 3, 4, 0, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddAtIndexAtEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(5, 0);

        Object[] array = {1, 2, 3, 4, 5, 0};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexOverBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(6, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAtIndexNull() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addAtIndex(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetUnderBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.get(-1);
    }

    @Test
    public void testGetZero() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.get(0), Integer.valueOf(1));
    }

    @Test
    public void testGetMidArray() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.get(2), Integer.valueOf(3));
    }

    @Test
    public void testGetBeforeEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.get(4), Integer.valueOf(5));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetAtEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.get(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOverBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.get(6);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexUnderBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.removeAtIndex(-1);
    }

    @Test
    public void testRemoveAtIndexZero() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.removeAtIndex(0), Integer.valueOf(1));

        Object[] array = {2, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveAtIndexMidArray() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);
        Assert.assertEquals(list.removeAtIndex(2), Integer.valueOf(3));

        Object[] array = {1, 2, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveAtIndexBeforeEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);
        Assert.assertEquals(list.removeAtIndex(4), Integer.valueOf(5));

        Object[] array = {1, 2, 3, 4};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexAtEnd() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.removeAtIndex(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexOverBounds() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.removeAtIndex(6);
    }

    @Test
    public void testAddToFrontEmpty() {
        list.addToFront(0);

        Object[] array = {0};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddToFrontOneNode() {
        list.addToBack(1);

        list.addToFront(0);

        Object[] array = {0, 1};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddToFrontMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addToFront(0);

        Object[] array = {0, 1, 2, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToFrontNull() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addToFront(null);
    }

    @Test
    public void testAddToBackEmpty() {
        list.addToBack(0);

        Object[] array = {0};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddToBackOneNode() {
        list.addToBack(1);

        list.addToBack(0);

        Object[] array = {1, 0};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testAddToBackMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addToBack(0);

        Object[] array = {1, 2, 3, 4, 5, 0};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToBackNull() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.addToBack(null);
    }

    @Test
    public void testRemoveFromFrontEmpty() {
        Assert.assertNull(list.removeFromFront());

        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveFromFrontOneNode() {
        list.addToBack(1);

        Assert.assertEquals(list.removeFromFront(), Integer.valueOf(1));

        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveFromFrontMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.removeFromFront(), Integer.valueOf(1));

        Object[] array = {2, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveFromBackEmpty() {
        Assert.assertNull(list.removeFromBack());

        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveFromBackOneNode() {
        list.addToBack(1);

        Assert.assertEquals(list.removeFromBack(), Integer.valueOf(1));

        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testRemoveFromBackMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.removeFromBack(), Integer.valueOf(5));

        Object[] array = {1, 2, 3, 4};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testToArrayEmpty() {
        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testToArrayOneNode() {
        list.addToBack(1);

        Object[] array = {1};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testToArrayMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Object[] array = {1, 2, 3, 4, 5};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testIsEmptyEmpty() {
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testIsEmptyMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testSizeEmpty() {
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void testSizeOneNode() {
        list.addToBack(1);

        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void testSizeMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertEquals(list.size(), 5);
    }

    @Test
    public void testClearEmpty() {
        list.clear();

        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testClearOneNode() {
        list.addToBack(1);

        list.clear();

        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testClearMultipleNodes() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        list.clear();

        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(list.size(), 0);
        Object[] array = {};
        Assert.assertArrayEquals(list.toArray(), array);
    }

    @Test
    public void testGetHeadEmpty() {
        Assert.assertNull(list.getHead());
    }

    @Test
    public void testGetHeadMultipleNodesInFront() {
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);

        Assert.assertNotNull(list.getHead());
        Assert.assertEquals(list.getHead().getData(), Integer.valueOf(1));
    }

    @Test
    public void testGetHeadMultipleNodesInBack() {
        list.addToFront(5);
        list.addToFront(4);
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);

        Assert.assertNotNull(list.getHead());
        Assert.assertEquals(list.getHead().getData(), Integer.valueOf(1));
    }
}