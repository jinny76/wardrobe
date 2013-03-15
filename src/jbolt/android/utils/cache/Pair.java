package jbolt.android.utils.cache;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class Pair<K extends Comparable, V> implements Comparable<Pair> {

    public Pair(K key1, V value1) {
        this.key = key1;
        this.value = value1;
    }

    public K key;
    public V value;

    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair p = (Pair) obj;
            return key.equals(p.key) && value.equals(p.value);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public int compareTo(Pair p) {
        int v = key.compareTo(p.key);
        if (v == 0) {
            if (p.value instanceof Comparable) {
                return ((Comparable) value).compareTo(p.value);
            }
        }
        return v;
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}