package ru.utsx.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionalCachedKeyValueStoreTest {
    private ITransactionalICachedKeyValueStore<String, String> store;

    @BeforeEach
    void setUp() {
        store = new TransactionalCachedKeyValueStore<>();
    }

    @Test
    void testPutAndGetNoTransaction() {
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1"));
    }

    @Test
    void testGetWithCaching() {
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1")); // Должно закэшироваться
        store.put("key1", "updatedValue");
        assertEquals("updatedValue", store.get("key1")); // Должно обновить кэш
    }

    @Test
    void testTransactionalPutAndCommit() {
        store.begin();
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1"));
        store.commit();
        assertEquals("value1", store.get("key1"));
    }

    @Test
    void testTransactionalPutAndRollback() {
        store.begin();
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1"));
        store.rollback();
        assertNull(store.get("key1"));
    }

    @Test
    void testTransactionalRemoveAndCommit() {
        store.put("key1", "value1");
        store.begin();
        store.remove("key1");
        assertNull(store.get("key1"));
        store.commit();
        assertNull(store.get("key1"));
    }

    @Test
    void testTransactionalRemoveAndRollback() {
        store.put("key1", "value1");
        store.begin();
        store.remove("key1");
        assertNull(store.get("key1"));
        store.rollback();
        assertEquals("value1", store.get("key1"));
    }

    @Test
    void testCommitUpdatesCache() {
        store.begin();
        store.put("key1", "value1");
        store.commit();
        assertEquals("value1", store.get("key1"));

        store.begin();
        store.put("key1", "newValue");
        store.commit();
        assertEquals("newValue", store.get("key1"));
    }

    @Test
    void testRollbackDoesNotUpdateCache() {
        store.begin();
        store.put("key1", "value1");
        store.commit();
        assertEquals("value1", store.get("key1"));

        store.begin();
        store.put("key1", "intermediateValue");
        store.rollback();
        assertEquals("value1", store.get("key1"));
    }

    @Test
    void testStartTransactionTwiceThrowsException() {
        store.begin();
        assertThrows(IllegalStateException.class, store::begin);
    }

    @Test
    void testCommitWithoutBeginThrowsException() {
        assertThrows(IllegalStateException.class, store::commit);
    }

    @Test
    void testRollbackWithoutBeginThrowsException() {
        assertThrows(IllegalStateException.class, store::rollback);
    }
}
