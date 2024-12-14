package ru.utsx.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionalKeyValueStore<K, V> implements ITransactionalKeyValueStore<K, V> {
    private final Map<K, V> store;
    private Map<K, V> transactionStore;
    private boolean inTransaction = false;

    public TransactionalKeyValueStore() {
        this.store = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        if (inTransaction) {
            transactionStore.put(key, value);
        } else {
            store.put(key, value);
        }
    }

    @Override
    public V get(K key) {
        if (inTransaction && transactionStore.containsKey(key)) {
            return transactionStore.get(key);
        }
        return store.get(key);
    }

    @Override
    public void remove(K key) {
        if (inTransaction) {
            transactionStore.put(key, null);
        } else {
            store.remove(key);
        }
    }

    @Override
    public void clear() {
        if (inTransaction) {
            transactionStore.clear();
        } else {
            store.clear();
        }
    }

    @Override
    public boolean contains(K key) {
        if (inTransaction) {
            return transactionStore.containsKey(key) || store.containsKey(key);
        }
        return store.containsKey(key);
    }

    @Override
    public Set<K> keys() {
        if (inTransaction) {
            Map<K, V> combined = new HashMap<>(store);
            transactionStore.forEach((k, v) -> {
                if (v != null) {
                    combined.put(k, v);
                } else {
                    combined.remove(k);
                }
            });
            return combined.keySet();
        }
        return store.keySet();
    }

    @Override
    public void putAll(Map<K, V> map) {
        if (inTransaction) {
            transactionStore.putAll(map);
        } else {
            store.putAll(map);
        }
    }

    @Override
    public void begin() {
        if (!inTransaction) {
            transactionStore = new HashMap<>();
            inTransaction = true;
        } else {
            throw new IllegalStateException("Transaction already in progress");
        }
    }

    @Override
    public void commit() {
        if (inTransaction) {
            transactionStore.forEach((k, v) -> {
                if (v == null) {
                    store.remove(k);
                } else {
                    store.put(k, v);
                }
            });
            transactionStore = null;
            inTransaction = false;
        } else {
            throw new IllegalStateException("No transaction in progress");
        }
    }

    @Override
    public void rollback() {
        if (inTransaction) {
            transactionStore = null;
            inTransaction = false;
        } else {
            throw new IllegalStateException("No transaction in progress");
        }
    }
}