import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ssenthilkumar3JUNIT {

    static final int TIMEOUT = 10000;
    private MaxHeap<Integer> heap;

    @Before
    public void setUp() {
        heap = new MaxHeap<Integer>();
    }

    @Test(timeout = TIMEOUT)
    public void initialConditions() {
        Integer[] backing = new Integer[HeapInterface.STARTING_SIZE];
        assertArrayEquals(backing, heap.getBackingArray());
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test(timeout = TIMEOUT)
    public void addAndBubbleUp() {

        Integer[] backing = new Integer[HeapInterface.STARTING_SIZE];
        // Add 1
        backing[1] = 1;
        heap.add(1);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 2
        backing[1] = 2;
        backing[2] = 1;
        heap.add(2);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 3
        backing[1] = 3;
        backing[3] = 2;
        heap.add(3);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 4
        backing[1] = 4;
        backing[2] = 3;
        backing[4] = 1;
        heap.add(4);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 5
        backing[1] = 5;
        backing[2] = 4;
        backing[5] = 3;
        heap.add(5);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 6
        backing[1] = 6;
        backing[3] = 5;
        backing[6] = 2;
        heap.add(6);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 7
        backing[1] = 7;
        backing[3] = 6;
        backing[7] = 5;
        heap.add(7);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 8
        backing[1] = 8;
        backing[2] = 7;
        backing[4] = 4;
        backing[8] = 1;
        heap.add(8);
        assertArrayEquals(backing, heap.getBackingArray());
        // Add 9
        backing[1] = 9;
        backing[2] = 8;
        backing[4] = 7;
        backing[9] = 4;
        heap.add(9);
        assertArrayEquals(backing, heap.getBackingArray());
        assertEquals(9, heap.size());
        // Add 10 - should resize array
        Integer[] resized = Arrays.copyOf(backing, 20);
        resized[1] = 10;
        resized[2] = 9;
        resized[5] = 8;
        resized[10] = 3;
        heap.add(10);
        assertArrayEquals(resized, heap.getBackingArray());
        assertEquals(10, heap.size());
        assertFalse(heap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void addRepeats() {

        // Same as root, swap right side
        for (int i = 1; i < 6; i++) {
            heap.add(i);
        }
        heap.add(5);
        Integer[] backing = {null, 5, 4, 5, 1, 3, 2, null, null, null};
        assertArrayEquals(backing, heap.getBackingArray());
        assertEquals(6, heap.size());

        // Same as root, swapping with left side
        heap.clear();
        for (int i = 1; i < 5; i++) {
            heap.add(i);
        }
        heap.add(4);
        Integer[] backing2 = {null, 4, 4, 2, 1, 3, null, null, null, null};
        assertArrayEquals(backing2, heap.getBackingArray());
        assertEquals(5, heap.size());

        // Same as left child, swapping with left side
        heap.clear();
        for (int i = 1; i < 8; i++) {
            heap.add(i);
        }
        heap.add(4);
        Integer[] backing3 = {null, 7, 4, 6, 4, 3, 2, 5, 1, null};
        assertArrayEquals(backing3, heap.getBackingArray());
        assertEquals(8, heap.size());

        // No swaps
        heap.clear();
        for (int i = 1; i < 11; i++) {
            heap.add(i);
        }
        heap.add(8);
        Integer[] backing4 = {null, 10, 9, 6, 7, 8, 2, 5, 1, 4, 3, 8, null, null, null, null, null, null, null, null};
        assertArrayEquals(backing4, heap.getBackingArray());
        assertEquals(11, heap.size());

        // Just add duplicates
        heap.clear();
        heap.add(1);
        heap.add(1);
        Integer[] backing5 = new Integer[10];
        backing5[1] = 1;
        backing5[2] = 1;
        assertArrayEquals(backing5, heap.getBackingArray());
        assertEquals(2, heap.size());

        heap.add(1);
        backing5[3] = 1;
        assertArrayEquals(backing5, heap.getBackingArray());
        assertEquals(3, heap.size());

        heap.add(2);
        backing5[4] = 1;
        backing5[1] = 2;
        assertArrayEquals(backing5, heap.getBackingArray());
        assertEquals(4, heap.size());

        heap.add(0);
        backing5[5] = 0;
        assertArrayEquals(backing5, heap.getBackingArray());
        assertEquals(5, heap.size());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addNull() {
        heap.add(null);
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void clearTest() {
        heap.add(1);
        heap.add(2);
        heap.add(3);
        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        Integer[] backing = new Integer[10];
        assertEquals(backing, heap.getBackingArray());

        for (int i = 1; i < 11; i++) {
            heap.add(i);
        }
        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        // Resized array should go back to original size
        assertEquals(backing, heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void removeTest() {
        for (int i = 1; i < 12; i++) {
            heap.add(i);
        }

        Integer[] backing = new Integer[20];
        int size = 11;
        for (int i = 1; i < 12; i++) {
            backing[i] = heap.remove();
            assertEquals(--size, heap.size());
        }

        Integer[] expected = {null, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, null, null, null, null, null, null, null, null};
        assertArrayEquals(expected, backing);
        assertEquals(0, heap.size());
    }

    @Test(timeout = TIMEOUT)
    public void removeWithDuplicatesTest() {
        heap.add(1);
        heap.add(1);
        heap.add(1);
        heap.add(2);
        Integer[] backing = new Integer[10];
        int size = 4;
        for (int i = 1; i < 5; i++) {
            backing[i] = heap.remove();
            assertEquals(--size, heap.size());
        }

        Integer[] expected = {null, 2, 1, 1, 1, null, null, null, null, null};
        assertArrayEquals(expected, backing);
        assertEquals(0, heap.size());

        heap.clear();
        heap.add(1);
        heap.add(1);
        heap.remove();
        expected[1] = 1;
        expected[2] = null;
        expected[3] = null;
        expected[4] = null;
        assertArrayEquals(expected, heap.getBackingArray());

        heap.clear();
        heap.add(1);
        heap.add(1);
        heap.add(1);
        heap.add(0);
        heap.add(0);
        heap.add(0);
        heap.add(0);
        heap.remove();
        expected[1] = 1;
        expected[2] = 1;
        expected[3] = 0;
        expected[4] = 0;
        expected[5] = 0;
        expected[6] = 0;
        assertEquals(expected, heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void removeWithNoElements() {
        heap.remove();
    }

    // I'm getting REALLY lazy
    @Test(timeout = TIMEOUT)
    public void randomTestEverything() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) { // Trying 1000 different heaps
            heap.clear();
            int numPossibleElements = random.nextInt(1001); // up to 1000 elements in the heap
            int size = 0;
            Integer[] allElements = new Integer[numPossibleElements];
            int allElementsIndex = 0;

            for (int j = 0; j < numPossibleElements; j++) {
                int x = random.nextInt(751); // Mostly unique numbers, 25% duplicate
                heap.add(x);
                allElements[allElementsIndex++] = x;
                size++;
                assertEquals(size, heap.size());
            }

            Integer[] removed = new Integer[size];
            for (int j = 0; j < size; j++) {
                removed[j] = heap.remove();
            }
            assertEquals(0, heap.size());
            // Terrible naming convention vv
            assertTrue(inDescendingOrderAndAllElementsWereUsed(removed, allElements));
        }
    }

    private boolean inDescendingOrderAndAllElementsWereUsed(Integer[] removed, Integer[] all) {
        ArrayList<Integer> removedList = new ArrayList<Integer>();
        ArrayList<Integer> allElements = new ArrayList<Integer>();

        for (Integer x : removed) {
            if (x != null) {
                removedList.add(x);
            }
        }

        for (Integer x : all) {
            if (x != null) {
                allElements.add(x);
            }
        }

        if (removedList.size() == 0 && allElements.size() == 0) {
            return true;
        }

        Integer previous = removedList.get(0);
        for (Integer x : removedList) {
            if (!allElements.contains(x)) {
                return false;
            } else {
                if (x > previous) {
                    return false;
                } else {
                    previous = x;
                    allElements.remove(x);
                }
            }
        }
        return allElements.size() == 0;
    }
}