import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class myJUNIT {
    private class Person implements Comparable<Person> {
        private int age;
        private String name;
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public boolean equals(Object other) {
            if (other == null) return false;
            if (!(other instanceof Person)) return false;
            Person that = (Person) other;
            return this.name.equals(that.name);
        }
        public int compareTo(Person other) {
            return this.name.compareTo(other.name);
        }
        public String toString() {
            return name + " " + age;
        }
    }

    private BST<Person> bst;

    @Before
    public void setup() {
        bst = new BST<>();
    }

    @Test
    public void testRemove() {
        Person p = new Person("Me", -1);
        Person x = new Person("Me", 18);
        bst.add(x);
        Person y = bst.remove(p);
        assertEquals(y.toString(), x.toString());
    }



}