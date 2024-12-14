package ru.utsx.dbs;

import ru.utsx.core.IKeyValueStore;

public interface IMasterDatabase<K, V> {
    void registerReplica(IReplicaDatabase<K, V> replica);

    void unRegisterReplica(IReplicaDatabase<K, V> replica);

    void replicateToReplicas(K key, V value);

    void replicateDeleteToReplica(K key);

    IKeyValueStore<K, V> getStore();
}
