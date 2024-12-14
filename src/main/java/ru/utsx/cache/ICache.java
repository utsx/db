package ru.utsx.cache;

public interface ICache<K, V> {
    void put(K key, V value);

    boolean contains(K key);

    V get(K key);

    void remove(K key);

    void clear();

}
