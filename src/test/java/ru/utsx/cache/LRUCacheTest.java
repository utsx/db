package ru.utsx.cache;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LRUCacheTest {

    private LRUCache<String, String> lruCache;

    @BeforeEach
    public void setUp() {
        lruCache = new LRUCache<>(3);  // Установка максимального размера кэша равным 3
    }

    @Test
    public void testPutAndGet() {
        lruCache.put("key1", "value1");
        assertEquals("value1", lruCache.get("key1"));
    }

    @Test
    public void testEviction() {
        lruCache.put("key1", "value1");
        lruCache.put("key2", "value2");
        lruCache.put("key3", "value3");
        lruCache.put("key4", "value4");  // Вытесняет "key1"

        assertNull(lruCache.get("key1"));
        assertEquals("value2", lruCache.get("key2"));
        assertEquals("value3", lruCache.get("key3"));
        assertEquals("value4", lruCache.get("key4"));
    }

    @Test
    public void testUpdateUsage() {
        lruCache.put("key1", "value1");
        lruCache.put("key2", "value2");
        lruCache.put("key3", "value3");
        lruCache.get("key1");  // Обновление использования "key1"
        lruCache.put("key4", "value4");  // Теперь "key2" будут вытеснено, а не "key1"

        assertNotNull(lruCache.get("key1"));
        assertNull(lruCache.get("key2"));  // "key2" вытеснено
        assertNotNull(lruCache.get("key3"));
        assertNotNull(lruCache.get("key4"));
    }

    @Test
    public void testRemove() {
        lruCache.put("key1", "value1");
        lruCache.remove("key1");
        assertNull(lruCache.get("key1"));
    }

    @Test
    public void testClear() {
        lruCache.put("key1", "value1");
        lruCache.put("key2", "value2");
        lruCache.clear();
        assertNull(lruCache.get("key1"));
        assertNull(lruCache.get("key2"));
    }

    @Test
    public void testContains() {
        lruCache.put("key1", "value1");
        assertTrue(lruCache.contains("key1"));
        assertFalse(lruCache.contains("key2"));
    }
}