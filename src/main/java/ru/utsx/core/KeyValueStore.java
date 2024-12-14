package ru.utsx.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class KeyValueStore<K, V> implements IKeyValueStore<K, V> {
    private final Map<K, V> store;

    public KeyValueStore() {
        this.store = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        store.put(key, value);
    }

    @Override
    public V get(K key) {
        return store.get(key);
    }

    @Override
    public void remove(K key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public boolean contains(K key) {
        return store.containsKey(key);
    }

    @Override
    public Set<K> keys() {
        return store.keySet();
    }

    @Override
    public void putAll(Map<K, V> map) {
        store.putAll(map);
    }
}