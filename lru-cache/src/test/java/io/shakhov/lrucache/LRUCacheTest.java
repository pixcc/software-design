package io.shakhov.lrucache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class LRUCacheTest {

    private LRUCache<Integer, Integer> lruCache;

    @Before
    public void initLRUCache() {
        lruCache = new LRUCacheImpl<>(2);
    }

    @Test
    public void testBasic() {
        lruCache.put(1, 1);
        assertEquals(1, lruCache.size());
        assertEquals(Integer.valueOf(1), lruCache.get(1));
    }

    @Test
    public void testBasicEviction() {
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(2), lruCache.get(2));
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertNull(lruCache.get(1));

        lruCache.put(4, 4);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertEquals(Integer.valueOf(4), lruCache.get(4));
        assertNull(lruCache.get(2));
    }

    @Test
    public void testEvictionWithGet() {
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.get(1);
        lruCache.put(3, 3);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(1), lruCache.get(1));
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertNull(lruCache.get(2));

        lruCache.put(4, 4);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertEquals(Integer.valueOf(4), lruCache.get(4));
        assertNull(lruCache.get(1));
    }

    @Test
    public void testEvictionWithReplace() {
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(1, 5);
        lruCache.put(3, 3);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(5), lruCache.get(1));
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertNull(lruCache.get(2));

        lruCache.put(4, 4);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertEquals(Integer.valueOf(4), lruCache.get(4));
        assertNull(lruCache.get(1));
    }

    @Test
    public void testTailReplace() {
        lruCache.put(1, 1);
        assertEquals(1, lruCache.size());
        assertEquals(Integer.valueOf(1), lruCache.get(1));

        lruCache.put(1, 2);
        assertEquals(1, lruCache.size());
        assertEquals(Integer.valueOf(2), lruCache.get(1));

        lruCache.put(2, 2);
        lruCache.put(3, 3);
        assertEquals(2, lruCache.size());
        assertEquals(Integer.valueOf(2), lruCache.get(2));
        assertEquals(Integer.valueOf(3), lruCache.get(3));
        assertNull(lruCache.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCapacity() {
        lruCache = new LRUCacheImpl<>(0);
    }
}

