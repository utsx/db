package ru.utsx.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionalKeyValueStoreTest {
    private ITransactionalKeyValueStore<String, String> store;

    @BeforeEach
    void setUp() {
        store = new TransactionalKeyValueStore<>();
    }

    @Test
    void testPutAndGetNoTransaction() {
        store.put("key1", "value1");
        assertEquals("value1", store.get("key1"));
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
        assertNull(store.get("key1")); // Должно быть null во время транзакции.
        store.rollback();
        assertEquals("value1", store.get("key1")); // Должно вернуться к "value1" после отката.
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