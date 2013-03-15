package jbolt.android.utils.cache;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public interface CacheListener<K, V> {

    void onAddCache(K key, V value);

    void onRemoveCache(K key, V value);
}
