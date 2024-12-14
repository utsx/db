package ru.utsx.core;

public interface ITransactionalKeyValueStore<K, V> extends
        IKeyValueStore<K, V> {

    void begin();

    void commit();

    void rollback();
}
