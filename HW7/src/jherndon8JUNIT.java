import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class jherndon8JUNIT {
    private AVL<MagicString> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<MagicString>();
    }
    @Test(timeout = 250)
    public void testRemoveHard() {
        avlTree.add(new MagicString("first", 646));
        avlTree.add(new MagicString("second", 477));
        avlTree.add(new MagicString("third", 856));
        avlTree.add(new MagicString("fourth", 526));
        avlTree.add(new MagicString("fifth", 386));
        avlTree.add(new MagicString("sixth", 747));
        avlTree.add(new MagicString("seventh", 900));
        avlTree.add(new MagicString("eighth", 570));
        avlTree.add(new MagicString("ninth", 1000));
        avlTree.add(new MagicString("tenth", 875));
        avlTree.add(new MagicString("11th", 800));
        avlTree.add(new MagicString("12th", 950));
        avlTree.add(new MagicString("13th", 300));
        avlTree.add(new MagicString("14th", 400));
        avlTree.add(new MagicString("15th", 330));
        avlTree.add(new MagicString("16th", 700));
        avlTree.add(new MagicString("17th", 750));
        avlTree.add(new MagicString("18th", 860));
        avlTree.add(new MagicString("19th", 650));
        avlTree.add(new MagicString("20th", 825));
        avlTree.add(new MagicString("21th", 815));

        //System.out.println(avlTree);
        //I'd recommend supporting toString() for debugging,
        //then you can see what the tree looks like before and after

        avlTree.remove(new MagicString("first", 646));
        System.out.println("Removed root");

        //System.out.println(avlTree);

        AVLNode<MagicString> root = avlTree.getRoot();
        assertEquals(new MagicString("sixth", 747), root.getData());
        assertEquals(4, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(new MagicString("eighth", 570), root.getLeft().getData());
        assertEquals(3, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals(new MagicString("third", 856), root.getRight().getData());
        assertEquals(3, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals(new MagicString("fifth", 386), root.getLeft().getLeft().getData());
        assertEquals(new MagicString("16th", 700), root.getLeft().getRight().getData());
        assertEquals(new MagicString("11th", 800), root.getRight().getLeft().getData());
    }

    @Test(timeout = 250)
    public void testRemoveHard2() {
        avlTree.add(new MagicString("", 500));
        avlTree.add(new MagicString("", 200));
        avlTree.add(new MagicString("", 800));
        avlTree.add(new MagicString("", 100));
        avlTree.add(new MagicString("", 350));
        avlTree.add(new MagicString("", 650));
        avlTree.add(new MagicString("", 1000));
        avlTree.add(new MagicString("", 50));
        avlTree.add(new MagicString("", 150));
        avlTree.add(new MagicString("", 275));
        avlTree.add(new MagicString("", 425));
        avlTree.add(new MagicString("", 600));
        avlTree.add(new MagicString("", 700));
        avlTree.add(new MagicString("", 900));
        avlTree.add(new MagicString("", 75));
        avlTree.add(new MagicString("", 125));
        avlTree.add(new MagicString("", 175));
        avlTree.add(new MagicString("", 300));
        avlTree.add(new MagicString("", 625));
        avlTree.add(new MagicString("", 130));

        avlTree.remove(new MagicString("", 500));

        AVLNode<MagicString> root = avlTree.getRoot();

        //System.out.println(root.getLeft().getRight().getData());
        //Should be 150

        assertEquals(new MagicString("", 425), root.getData());
        assertEquals(4, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(new MagicString("", 150), root.getLeft().getData());
        assertEquals(3, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(new MagicString("", 200), root.getLeft().getRight().getData());
        assertEquals(new MagicString("", 300), root.getLeft().getRight().getRight().getData());
        assertEquals(new MagicString("", 350), root.getLeft().getRight().getRight().getRight().getData());
        assertEquals(new MagicString("", 175), root.getLeft().getRight().getLeft().getData());
        assertEquals(new MagicString("", 100), root.getLeft().getLeft().getData());
        assertEquals(new MagicString("", 125), root.getLeft().getLeft().getRight().getData());
    }

    @Test(timeout = 5000)
    public void testRandomTrees() {
        java.util.Random random = new java.util.Random(System.currentTimeMillis());
        java.util.Set<MagicString> set = new java.util.HashSet<>();
        int j;
        for (int i = 0; i < 20000; i++) {
            j = Math.abs(random.nextInt());
            //System.out.println(j);
            set.add(new MagicString("", j));
            avlTree.add(new MagicString("", j));
        }
        //System.out.println(avlTree);
        //System.out.println(avlTree.size());
        //System.out.println(set.size());
        testNode(avlTree.getRoot());
        for (MagicString s : set) {
            //System.out.println(s);
            avlTree.remove(s);
            testNode(avlTree.getRoot());
        }
    }

    public void testNode(AVLNode node) {
        if (node == null) {
            return;
        }
        if (node.getHeight() == 0) {
            assertNull(node.getLeft());
            assertNull(node.getRight());
            assertEquals(0, node.getBalanceFactor());
            return;
        } else {
            assertTrue((node.getLeft() != null && node.getLeft().getHeight() ==
                    node.getHeight() - 1) || (node.getRight() != null
                    && node.getRight().getHeight() == node.getHeight() - 1));
        }
        if (node.getRight() == null) {
            if (node.getLeft() == null) {
                //System.out.println("-1 -1 " + node.getBalanceFactor());
                assertEquals(0, node.getBalanceFactor());
            } else {
                //System.out.println("" + node.getLeft().getHeight() + " -1 " + node.getBalanceFactor());
                assertEquals(node.getLeft().getHeight() + 1, node.getBalanceFactor());
            }
        } else if (node.getLeft() == null) {
            //System.out.println("-1 " + node.getRight().getHeight() + " " + node.getBalanceFactor());
            assertEquals(node.getRight().getHeight() * -1 - 1, node.getBalanceFactor());
        } else {
            //System.out.println("" + node.getLeft().getHeight() + " " + node.getRight().getHeight() + " " + node.getBalanceFactor());
            assertEquals(node.getLeft().getHeight() - node.getRight().getHeight(), node.getBalanceFactor());
        }
        assertTrue(node.getBalanceFactor() < 2 && node.getBalanceFactor() > -2);

        if (node.getRight() != null) {
            testNode(node.getRight());
        }
        if (node.getLeft() != null) {
            testNode(node.getLeft());
        }
    }

    private class MagicString implements Comparable<MagicString> {
        private final String magicString;
        private final int number;

        /**
         * Create a MagicString.
         *
         * @param magicString random string to store
         * @param number number to store
         */
        public MagicString(String magicString, int number) {
            this.magicString = magicString;
            this.number = number;
        }

        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (!(other instanceof MagicString)) {
                return false;
            }
            MagicString that = (MagicString) other;
            return that.number == number;
        }

        @Override
        public int compareTo(MagicString other) {
            return number - other.number;
        }

        @Override
        public String toString() {
            return "MagicString: " + magicString + ", " + number;
        }
    }
}