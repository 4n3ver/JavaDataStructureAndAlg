import java.util.HashSet;
import java.util.Set;

/**
 * Class for implementing your HashMap with external chaining Created by
 * CS1332TAs on 2/11/15.
 *
 * @author Yoel Ivan
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {
    private MapEntry<K, V>[] backing;
    private int size;

    /**
     * Construct {@link HashMap} object.
     */
    public HashMap() {
        this.backing = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(K key, V value) {
        checkArg(key);
        checkArg(value);
        checkCap();
        MapEntry<K, V> currentEntry = backing[indexOf(key)];
        if (currentEntry == null) {
            // bucket still empty
            backing[indexOf(key)] = new MapEntry<>(key, value);
        } else {
            while (currentEntry.getNext() != null) {
                currentEntry = currentEntry.getNext();
            }
            currentEntry.setNext(new MapEntry<>(key, value));
        }
        size++;
    }

    /**
     * Check whether LOAD_FACTOR is exceeded
     */
    private void checkCap() {
        if ((size + 1.0) / backing.length >= MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] oldTable = backing;
            backing = new MapEntry[2 * backing.length + 1];
            size = 0;
            for (MapEntry<K, V> entry : oldTable) {
                if (entry != null) {
                    add(entry.getKey(), entry.getValue());
                    while (entry.getNext() != null) {
                        add(entry.getNext().getKey(),
                                entry.getNext().getValue());
                        entry = entry.getNext();
                    }
                }
            }
        }
    }

    /**
     * Computes indexing based on hash code
     *
     * @param key key which index is to be computed
     * @return index where the key should be placed on
     */
    private int indexOf(K key) {
        return key.hashCode() % backing.length;
    }

    /**
     * Validate objects.
     *
     * @param o object to be validated
     */
    private void checkArg(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("null arg");
        }
    }

    @Override
    public Set<MapEntry<K, V>> getEntrySet() {
        Set<MapEntry<K, V>> ret = new HashSet<>();
        for (MapEntry<K, V> entry : backing) {
            if (entry != null) {
                ret.add(entry);
                while (entry.getNext() != null) {
                    ret.add(entry.getNext());
                    entry = entry.getNext();
                }
            }
        }
        return ret;
    }

    @Override
    public MapEntry<K, V>[] getBackingArray() {
        return backing;
    }

    @Override
    public int size() {
        return size;
    }
}
