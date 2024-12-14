package ru.utsx.failover;

import java.util.Collection;

import ru.utsx.dbs.IReplicaDatabase;

public interface IFailoverCoordinator<K, V> {

    void initFailover();

    IReplicaDatabase<K, V> selectNewMaster(Collection<IReplicaDatabase<K, V>> replicas);

    void performFailover(IReplicaDatabase<K, V> newMaster);

    boolean isFailoverInProgress();
}
