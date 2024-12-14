package ru.utsx.replication;

import java.util.Set;

import ru.utsx.dbs.IReplicaDatabase;

public interface IReplicationManager<K, V> {

    void startReplication();

    void stopReplication();

    boolean isReplicationStarted();

    Set<IReplicaDatabase<K, V>> getReplicas();
}
