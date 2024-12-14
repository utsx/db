package ru.utsx.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CachedKeyValueStoreTest {
    private ICachedKeyValueStore<String, String> store;

    @BeforeEach
    void setUp() {
        store = new CachedKeyValueStore<>();
    }

    @Test
    void testPutAndGet() {
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1"));
    }

    @Test
    void testGetNonExistentKey() {
        assertNull(store.get("nonexistent"));
    }

    @Test
    void testContains() {
        store.put("key1", "value1");
        assertTrue(store.contains("key1"));
        assertFalse(store.contains("key2"));
    }

    @Test
    void testRemove() {
        store.put("key1", "value1");
        store.remove("key1");
        assertFalse(store.contains("key1"));
        assertNull(store.get("key1"));
    }

    @Test
    void testClear() {
        store.put("key1", "value1");
        store.put("key2", "value2");
        store.clear();
        assertFalse(store.contains("key1"));
        assertFalse(store.contains("key2"));
        assertNull(store.get("key1"));
        assertNull(store.get("key2"));
    }

    @Test
    void testPutAll() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        store.putAll(map);

        assertEquals("value1", store.get("key1"));
        assertEquals("value2", store.get("key2"));
    }

    @Test
    void testKeys() {
        store.put("key1", "value1");
        store.put("key2", "value2");
        assertTrue(store.keys().contains("key1"));
        assertTrue(store.keys().contains("key2"));
        assertEquals(2, store.keys().size());
    }
}