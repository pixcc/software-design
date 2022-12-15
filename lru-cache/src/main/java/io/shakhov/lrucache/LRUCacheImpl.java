package io.shakhov.lrucache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class LRUCacheImpl<K, V> extends LRUCache<K, V> {
    private final Map<K, Entry<K, V>> entries;
    private final Entry<K, V> head;
    private Entry<K, V> tail;

    public LRUCacheImpl(int capacity) {
        super(capacity);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.entries = new HashMap<>();
        Entry<K, V> dummy = new Entry<>(null, null);
        this.head = dummy;
        this.tail = dummy;
    }

    @Override
    protected int doSize() {
        return entries.size();
    }

    @Override
    protected void doPut(@Nonnull K key, @Nonnull V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        if (entries.containsKey(key)) {
            Entry<K, V> oldEntry = entries.get(key);
            removeEntry(oldEntry);
        }
        entries.put(key, entry);
        addEntryToTail(entry);
        evictIfNeeded();
    }

    @Override
    @Nullable
    protected V doGet(@Nonnull K key) {
        Entry<K, V> entry = entries.get(key);
        if (entry != null) {
            removeEntry(entry);
            addEntryToTail(entry);
            return entry.value;
        } else {
            return null;
        }
    }

    @Override
    @Nonnull
    protected K getMostRecentKey() {
        return tail.key;
    }

    private void addEntryToTail(@Nonnull Entry<K, V> entry) {
        tail.next = entry;
        entry.prev = tail;
        tail = entry;
    }

    private void removeEntry(@Nonnull Entry<K, V> entry) {
        if (tail == entry) {
            tail = entry.prev;
        }
        if (entry.prev != null) {
            entry.prev.next = entry.next;
        }
        if (entry.next != null) {
            entry.next.prev = entry.prev;
        }
        entry.prev = null;
        entry.next = null;
    }

    private void evictIfNeeded() {
        if (entries.size() > capacity) {
            Entry<K, V> toRemove = head.next;
            entries.remove(toRemove.key);
            removeEntry(toRemove);
        }
    }

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> prev;
        Entry<K, V> next;

        Entry(@Nullable K key, @Nullable V value) {
            this.key = key;
            this.value = value;
        }
    }
}
