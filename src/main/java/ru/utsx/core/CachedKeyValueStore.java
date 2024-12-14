package ru.utsx.core;

import java.util.Map;
import java.util.Set;

import lombok.Getter;
import ru.utsx.cache.ICache;
import ru.utsx.cache.LRUCache;

@Getter
public class CachedKeyValueStore<K, V> implements ICachedKeyValueStore<K, V> {
    private final ICache<K, V> cache;
    private final IKeyValueStore<K, V> store;

    public CachedKeyValueStore() {
        this.cache = new LRUCache<>(10);
        this.store = new KeyValueStore<>();
    }

    @Override
    public void put(K key, V value) {
        store.put(key, value);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = store.get(key);
            if (value != null) {
                cache.put(key, value);
            }
        }
        return value;
    }

    @Override
    public void remove(K key) {
        store.remove(key);
        cache.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
        cache.clear();
    }

    @Override
    public boolean contains(K key) {
        return cache.contains(key) || store.contains(key);
    }

    @Override
    public Set<K> keys() {
        return store.keys();
    }

    @Override
    public void putAll(Map<K, V> map) {
        store.putAll(map);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            cache.put(entry.getKey(), entry.getValue());
        }
    }
}