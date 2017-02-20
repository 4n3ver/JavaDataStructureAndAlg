import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementation of {@link HashMapInterface}.
 *
 * @param <K> type of the key
 * @param <V> type of the value
 * @author Yoel Ivan
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        // Initialize your hashtable here.
        clear();
    }

    @Override
    public V add(K key, V value) {
        checkArg(key);
        checkArg(value);
        checkCap();
        return add(new MapEntry<>(key, value));
    }

    /**
     * Alternate add method to prevent recreating {@link MapEntry} object on
     * re-hashing.
     *
     * @param entry {@link MapEntry} to be added to the table
     * @return value of the old entry if the key is duplicate <code>null</code>
     * otherwise
     */
    private V add(MapEntry<K, V> entry) {
        int index = indexOf(entry.getKey());
        while (index == -1) {
            reSize(2 * table.length + 1);
            index = indexOf(entry.getKey());
        }
        V oldVal = null;
        if (table[index] != null && !table[index].isRemoved()) {
            oldVal = table[index].getValue();
        } else {
            size++;
        }
        table[index] = entry;
        return oldVal;
    }

    /**
     * Calculate suitable index for a given key.
     *
     * Case when to stop iterating
     * # found null
     * # found equal key that has not been removed
     * # iterated N(table.length) times already
     *
     * @param key key which index is to be calculated
     * @return the index
     */
    private int indexOf(K key) {
        int index = Math.abs(key.hashCode()) % table.length;
        int pivot = index;
        int removedIndex = -1;
        boolean noPlace = false;

        /*
         * if we hit NULL index, we know there must not any un-removed
         * duplicate key further in the table
         * NULL index is the barrier between traversed index and un-traversed
         * index
         */
        for (int i = 1; !noPlace && table[index] != null; i++) {
            if (i >= table.length) {
                index = -1;
                noPlace = true;
            } else {
                if (table[index].getKey().equals(key)
                    && !table[index].isRemoved()) {

                    // immediately return since we have to replace the previous
                    // key
                    return index;
                } else if (removedIndex == -1 && table[index].isRemoved()) {

                    /*
                     * memorize the first removed index encountered
                     * we don't immediately return since we want to make sure
                     * there is no un-removed duplicate key later in the table
                     */
                    removedIndex = index;
                }
                index = (pivot + i * i) % table.length;
            }
        }

        /*
         * after we iterate N times and sure there is no un-removed duplicate
         * key, we use the first encountered removedIndex
         * There is no NULL space in this case
         * OR
         * if there is, the first
         * encountered removedIndex must come before the NULL index, thus we
         * use removedIndex
         * (always true in this case)->(removedIndex < NULLindex)
         */
        if (removedIndex != -1) {
            index = removedIndex;
        }
        return index;
    }

    @Override
    public V remove(K key) {
        checkArg(key);
        int index = indexOf(key);
        if (!(table[index] != null && !table[index].isRemoved()
                && table[index].getKey().equals(key))) {
            throw new NoSuchElementException("not found");
        } else {

            /*
             * The key exist in the table iff:
             * table[index] is not NULL
             * table[index].key == key
             * table[index] has not been removed yet
             */
            V val = table[index].getValue();
            table[index].setRemoved(true);
            size--;
            return val;
        }

    }

    @Override
    public V get(K key) {
        checkArg(key);
        int index = indexOf(key);
        if (table[index] != null && !table[index].isRemoved() && table[index]
                .getKey().equals(key)) {
            return table[index].getValue();
        } else {
            throw new NoSuchElementException("not found");
        }
    }

    @Override
    public boolean contains(K key) {
        checkArg(key);
        int index = indexOf(key);
        return table[index] != null && !table[index].isRemoved()
                && table[index].getKey().equals(key);
    }

    @Override
    public void clear() {
        table = new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        Set<K> list = new HashSet<>(size);
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    @Override
    public List<V> values() {
        ArrayList<V> list = new ArrayList<>(size);
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    /**
     * Check whether the table exceed the load factor and resize if load factor
     * exceeded.
     */
    private void checkCap() {
        // 1.0 to truncation, also add 1 since we want to make sure newly
        // added element fit to the table
        // load_factor = n / N, n num of entry, N len of table
        if ((size + 1.0) / table.length > MAX_LOAD_FACTOR) {
            reSize(2 * table.length + 1);
        }
    }

    /**
     * Resizing the current table to the given size.
     *
     * @param newSize new size which table need to be resized to.
     */
    private void reSize(int newSize) {
        MapEntry<K, V>[] oldTable = table;
        table = new MapEntry[newSize];
        size = 0;
        for (MapEntry<K, V> entry : oldTable) {
            if (entry != null && !entry.isRemoved()) {
                add(entry);
            }
        }
    }

    /**
     * Validate an argument.
     *
     * @param arg argument to be validated.
     */
    private void checkArg(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("argument(s) is null");
        }
    }
}