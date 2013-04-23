package jbolt.android.utils.cache;

import java.io.Serializable;
import java.util.*;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class LRUCache<K extends Comparable, V> implements Cache<K, V>, Serializable {

    Map<K, Item> dataMap = Collections.synchronizedMap(new HashMap<K, Item>());
    Item startItem = new Item();
    Item endItem = new Item();
    int maxSize;
    Object lock = new Object();
    List<CacheListener> listeners = new ArrayList<CacheListener>();

    public LRUCache(int maxObjects) {
        maxSize = maxObjects;
        startItem.next = endItem;
        endItem.previous = startItem;
    }

    void removeItem(Item item) {
        synchronized (lock) {
            item.previous.next = item.next;
            item.next.previous = item.previous;
        }
    }

    void insertHead(Item item) {
        synchronized (lock) {
            item.previous = startItem;
            item.next = startItem.next;
            startItem.next.previous = item;
            startItem.next = item;
        }
    }

    void moveToHead(Item item) {
        synchronized (lock) {
            item.previous.next = item.next;
            item.next.previous = item.previous;
            item.previous = startItem;
            item.next = startItem.next;
            startItem.next.previous = item;
            startItem.next = item;
        }
    }

    @SuppressWarnings("unchecked")
    public Pair[] getAll() {
        Pair p[] = new Pair[maxSize];
        int count = 0;
        synchronized (lock) {
            Item cur = startItem.next;
            while (cur != endItem) {
                p[count] = new Pair(cur.key, cur.value);
                ++count;
                cur = cur.next;
            }
        }
        Pair np[] = new Pair[count];
        System.arraycopy(p, 0, np, 0, count);
        return np;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        Item cur = dataMap.get(key);
        if (cur == null) {
            return null;
        }
        if (System.currentTimeMillis() > cur.expires) {
            for (CacheListener listener : listeners) {
                listener.onRemoveCache(cur.key, cur.value);
            }
            dataMap.remove(cur.key);
            removeItem(cur);
            return null;
        }
        if (cur != startItem.next) {
            moveToHead(cur);
        }
        return (V) cur.value;
    }

    public void put(K key, V obj) {
        put(key, obj, -1);
    }

    public void put(K key, V value, long validTime) {
        Item cur = dataMap.get(key);
        if (cur != null) {
            cur.value = value;
            if (validTime > 0) {
                cur.expires = System.currentTimeMillis() + validTime;
            } else {
                cur.expires = Long.MAX_VALUE;
            }
            moveToHead(cur);
            return;
        }
        if (dataMap.size() >= maxSize) {
            cur = endItem.previous;
            for (CacheListener listener : listeners) {
                listener.onRemoveCache(cur.key, cur.value);
            }
            dataMap.remove(cur.key);
            removeItem(cur);
        }
        long expires = 0;
        if (validTime > 0) {
            expires = System.currentTimeMillis() + validTime;
        } else {
            expires = Long.MAX_VALUE;
        }
        Item item = new Item(key, value, expires);
        insertHead(item);
        dataMap.put(key, item);
        for (CacheListener listener : listeners) {
            listener.onAddCache(key, value);
        }
    }

    public void remove(K key) {
        Item cur = dataMap.get(key);
        if (cur == null) {
            return;
        }
        for (CacheListener listener : listeners) {
            listener.onRemoveCache(key, get(key));
        }
        dataMap.remove(key);
        removeItem(cur);
    }

    public int size() {
        return dataMap.size();
    }

    public void addCacheListener(CacheListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    static class Item {

        public Item(Comparable k, Object v, long e) {
            key = k;
            value = v;
            expires = e;
        }

        public Item() {
        }

        public Comparable key;
        public Object value;
        public long expires;
        public Item previous;
        public Item next;
    }
}