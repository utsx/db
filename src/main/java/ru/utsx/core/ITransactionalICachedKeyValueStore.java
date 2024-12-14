package ru.utsx.core;

public interface ITransactionalICachedKeyValueStore<K, V>
        extends ICachedKeyValueStore<K, V>, ITransactionalKeyValueStore<K, V> {

}
