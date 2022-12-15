package io.shakhov.lrucache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class LRUCache<K, V> {

    protected final int capacity;

    protected LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(@Nonnull K key, @Nonnull V value) {
        assert key != null : "key is null";
        assert value != null : "value is null";
        int oldSize = size();
        boolean isReplace = get(key) != null;
        doPut(key, value);
        assert (isReplace && size() == oldSize)
                || size() == Math.min(capacity, oldSize + 1)
                : "incorrect size after put";
        assert getMostRecentKey() == key : "key is not most recent after put";
        assert get(key) == value : "key is not associated with value";
    }

    @Nullable
    public V get(@Nonnull K key) {
        assert key != null : "key is null";
        int oldSize = size();
        V value = doGet(key);
        assert size() == oldSize : "size changed after get";
        assert (getMostRecentKey() == key || value == null) : "key is not most recent after get";
        return value;
    }

    public int size() {
        int size = doSize();
        assert size >= 0 && size <= capacity : "size is out of bounds: " + size;
        return size;
    }

    protected abstract void doPut(@Nonnull K key, @Nonnull V value);

    @Nullable
    protected abstract V doGet(@Nonnull K key);

    protected abstract int doSize();

    @Nonnull
    protected abstract K getMostRecentKey();
}
