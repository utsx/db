package ru.utsx.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ru.utsx.cache.ICache;
import ru.utsx.cache.LRUCache;

@Getter
public class TransactionalCachedKeyValueStore<K, V> implements ITransactionalICachedKeyValueStore<K, V> {

    private final Map<K, V> store;
    private final ICache<K, V> cache;
    private Map<K, V> transactionStore;
    private boolean inTransaction = false;

    public TransactionalCachedKeyValueStore() {
        this.store = new HashMap<>();
        this.cache = new LRUCache<>(10);
    }

    @Override
    public void put(K key, V value) {
        if (inTransaction) {
            transactionStore.put(key, value);
        } else {
            store.put(key, value);
            cache.put(key, value);
        }
    }

    @Override
    public V get(K key) {
        if (inTransaction && transactionStore.containsKey(key)) {
            return transactionStore.get(key);
        }
        V value = cache.get(key);
        if (value == null) {
            value = store.get(key);
            if (value != null) {
                cache.put(key, value);
            }
        }
        if (value == null) {
            throw new UnsupportedOperationException("Not value presented in key");
        }
        return value;
    }

    @Override
    public void remove(K key) {
        if (inTransaction) {
            transactionStore.put(key, null);
        } else {
            store.remove(key);
            cache.remove(key);
        }
    }

    @Override
    public void clear() {
        if (inTransaction) {
            transactionStore.clear();
        } else {
            store.clear();
            cache.clear();
        }
    }

    @Override
    public boolean contains(K key) {
        if (inTransaction) {
            return transactionStore.containsKey(key) || store.containsKey(key);
        }
        return cache.contains(key) || store.containsKey(key);
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
            map.forEach(cache::put);
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
                    cache.remove(k);
                } else {
                    store.put(k, v);
                    cache.put(k, v);
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
