package ru.utsx.dbs;

import java.util.HashSet;
import java.util.Set;

import ru.utsx.core.IKeyValueStore;

public class MasterDatabase<K, V> implements IMasterDatabase<K, V> {

    private final Set<IReplicaDatabase<K, V>> replicas;
    private final IKeyValueStore<K, V> store;

    public MasterDatabase(IKeyValueStore<K, V> store) {
        this.replicas = new HashSet<>();
        this.store = store;
    }

    @Override
    public void registerReplica(IReplicaDatabase<K, V> replica) {
        replicas.add(replica);
    }

    @Override
    public void unRegisterReplica(IReplicaDatabase<K, V> replica) {
        replicas.remove(replica);
    }

    @Override
    public void replicateToReplicas(K key, V value) {
        for (IReplicaDatabase<K, V> replica : replicas) {
            replica.store().put(key, value);
        }
    }

    @Override
    public void replicateDeleteToReplica(K key) {
        for (IReplicaDatabase<K, V> replica : replicas) {
            replica.store().remove(key);
        }
    }

    @Override
    public IKeyValueStore<K, V> getStore() {
        return store;
    }
}