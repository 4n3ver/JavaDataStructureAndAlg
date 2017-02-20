import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Yoel Ivan on 1/10/2015.
 */
public class jhemdon8JUNIT {
    private LinkedListInterface<String> list;

    @Before
    public void setup() {
        list = new CircularLinkedList<String>();
    }

    @Test(timeout = 200)
    public void testNull() {
        try {
            list.addToFront(null);
            assertEquals("You Added", "Null Data");
        } catch (IllegalArgumentException e) {
        }
        assertEquals(list.removeFromFront(), null);
        assertEquals(list.removeFromBack(), null);
    }

    @Test(timeout = 200)
    public void testArray() { //Now testing removal and emptiness!

        list.addToFront("3");
        //[3]

        list.addToFront("2");
        //[2, 3]

        list.addToFront("1");
        //[1, 2, 3]

        list.addToBack("4");
        //[1, 2, 3, 4]

        list.addToBack("6");
        //[1, 2, 3, 4, 6]

        list.addAtIndex(4, "5");
        //[1, 2, 3, 4, 5, 6]

        Object[] testMe = {"1", "2", "3", "4", "5", "6"};
        for (int i = 0; i < 6; i++) {
            assertEquals(testMe[i], list.toArray()[i]);
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(list.removeAtIndex(4 - i), testMe[4-i]);
            assertTrue(!list.isEmpty());
        }

        assertEquals(list.removeFromFront(), "1"); //this
        assertTrue(!list.isEmpty());
        assertEquals(list.removeFromBack(), "6"); //that

        //swap lines this and that if you're nervous, ^
        //the test still works if you're not removing from the back properly

        assertTrue(list.isEmpty());
    }
}
