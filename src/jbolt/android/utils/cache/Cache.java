package jbolt.android.utils.cache;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public interface Cache<K extends Comparable, V> {

    V get(K obj);

    void put(K key, V obj);

    void put(K key, V obj, long validTime);

    void remove(K key);

    Pair[] getAll();

    int size();
}
