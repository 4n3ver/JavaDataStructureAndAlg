import java.util.NoSuchElementException;

public class PanicExamHashTableLP<K, V> {
    private Entry<K, V> arr[];

    public PanicExamHashTableLP() {
        arr = new Entry[10];
    }

    public static void main(String[] args) {
        PanicExamHashTableLP<Integer, String> dad = new PanicExamHashTableLP<>();
        dad.add(50, "Hey");
        System.out.println(dad.toString());
        dad.add(500, "IY?");
        System.out.println(dad.toString());
        dad.add(1500, "LOH");
        dad.add(20, "Hey9");
        dad.add(10, "Heyo");
        dad.add(200, "Hey");
        System.out.println(dad.toString());
        System.out.println(dad.add(500, "WAH"));
        System.out.println(dad.toString());
    }

    public V add(K key, V val) {
        int index = indexOf(key);
        if (arr[index] == null || arr[index].del) {
            arr[index] = new Entry<>(key, val);
            return null;
        } else {
            V old = arr[index].val;
            arr[index].val = val;
            return old;
        }
    }

    public int indexOf(K key) {
        int index = key.hashCode() % arr.length;
        int pivot = index;
        int old = -1;
        for (int i = 1; arr[index] != null; i++) {
            if (i >= arr.length) {
                throw new IllegalStateException();
            } else if (arr[index].key.equals(key) && !arr[index].del) {
                return index;
            } else if (old == -1 && arr[index].del) {
                old = index;
            }
            index = pivot + i;
        }
        return old != -1 ? old : index;
    }

    public V search(K key) {
        int index = indexOf(key);
        if (arr[index] == null || !arr[index].key.equals(key) || arr[index].del) {
            throw new NoSuchElementException();
        } else {
            return arr[index].val;
        }
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
        private boolean del;

        private Entry(K key, V val) {
            this.key = key;
            this.val = val;
            del = false;
        }

        public String toString() {
            String ret = "";
            if (del) {
                ret += "*";
            }
            return "(" + key + ", " + val + ")";
        }
    }
}