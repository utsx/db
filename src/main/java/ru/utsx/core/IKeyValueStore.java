package ru.utsx.core;

import java.util.Map;
import java.util.Set;

public interface IKeyValueStore<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    void clear();

    boolean contains(K key);

    Set<K> keys();

    void putAll(Map<K, V> prev);

}
