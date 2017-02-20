import java.util.NoSuchElementException;

public class PanicExamHashTable<K, V> {
    private Entry<K, V> arr[];

    public PanicExamHashTable() {
        arr = new Entry[10];
    }

    public static void main(String[] args) {
        PanicExamHashTable<Integer, String> dad =
                new PanicExamHashTable<>();
        dad.add(5, "Hey");
        System.out.println(dad.toString());
        dad.add(5, "IY?");
        System.out.println(dad.toString());
        dad.add(15, "LOH");
        dad.add(8, "Hey");
        dad.add(1, "Hey");
        dad.add(2, "Hey");
        System.out.println(dad.toString());
        System.out.println(dad.add(15, "WAH"));
        System.out.println(dad.toString());
    }

    public V add(K key, V val) {
        int index = key.hashCode() % arr.length;
        if (arr[index] == null) {
            arr[index] = new Entry<>(key, val);
        } else {
            Entry<K, V> curr = arr[index];
            while (curr.next != null || curr.key.equals(key)) {
                if (curr.key.equals(key)) {
                    V old = curr.val;
                    curr.val = val;
                    return old;
                } else {
                    curr = curr.next;
                }
            }
            curr.next = new Entry<>(key, val);
        }
        return null;
    }

    public V search(K key) {
        int index = key.hashCode() % arr.length;
        Entry<K, V> curr = arr[index];
        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr.val;
            } else {
                curr = curr.next;
            }
        }
        throw new NoSuchElementException("OMG");
    }

    public String toString() {
        String ret = "";
        for (Entry<K, V> mom : arr) {
            if (mom == null) {
                ret += "(null) ";
            } else if (mom != null) {
                ret += mom + " ";
            }
        }
        return ret;
    }

    private static class Entry<K, V> {
        private K key;
        private V val;
        private Entry<K, V> next;

        private Entry(K key, V val) {
            this.key = key;
            this.val = val;
            next = null;
        }

        public String toString() {
            String ret = "(" + key + ", " + val + ", ";
            if (next == null) {
                ret += "null";
            } else {
                ret += next;
            }

            return ret + ")";
        }
    }
}