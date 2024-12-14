package ru.utsx.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeyValueStoreTest {

    private KeyValueStore<String, String> keyValueStore;

    @BeforeEach
    public void setUp() {
        keyValueStore = new KeyValueStore<>();
    }

    @Test
    public void testPutAndGet() {
        keyValueStore.put("key1", "value1");
        assertEquals("value1", keyValueStore.get("key1"));
    }

    @Test
    public void testGetNonExistentKey() {
        assertNull(keyValueStore.get("nonExistentKey"));
    }

    @Test
    public void testRemoveKey() {
        keyValueStore.put("key1", "value1");
        keyValueStore.remove("key1");
        assertNull(keyValueStore.get("key1"));
    }

    @Test
    public void testClear() {
        keyValueStore.put("key1", "value1");
        keyValueStore.put("key2", "value2");
        keyValueStore.clear();
        assertNull(keyValueStore.get("key1"));
        assertNull(keyValueStore.get("key2"));
        assertTrue(keyValueStore.keys().isEmpty());
    }

    @Test
    public void testContains() {
        keyValueStore.put("key1", "value1");
        assertTrue(keyValueStore.contains("key1"));
        assertFalse(keyValueStore.contains("key2"));
    }

    @Test
    public void testKeys() {
        keyValueStore.put("key1", "value1");
        keyValueStore.put("key2", "value2");
        Set<String> keys = keyValueStore.keys();
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
        assertEquals(2, keys.size());
    }

    @Test
    public void testPutAll() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        keyValueStore.putAll(map);

        assertEquals("value1", keyValueStore.get("key1"));
        assertEquals("value2", keyValueStore.get("key2"));
    }
}