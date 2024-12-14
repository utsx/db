package ru.utsx.core;

import ru.utsx.cache.ICache;

public interface ICachedKeyValueStore<K, V>
        extends IKeyValueStore<K, V> {

    void put(K key, V value);

    V get(K key);

    void clear();
}
