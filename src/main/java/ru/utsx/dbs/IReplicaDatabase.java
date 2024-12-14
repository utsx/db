package ru.utsx.dbs;

import java.util.Map;

import ru.utsx.core.IKeyValueStore;

public interface IReplicaDatabase<K, V> {

    IKeyValueStore<K, V> store();

    default void put(K key, V value) {
        throw new UnsupportedOperationException("Can't put data in replica");
    }

    default void remove(K key) {
        throw new UnsupportedOperationException("Can't remove data in replica");
    }

    default void putAll(Map<K, V> prev) {
        throw new UnsupportedOperationException("Can't put all data in replica");
    }

    default void clear() {
        throw new UnsupportedOperationException("Can't put all data in replica");
    }
}
