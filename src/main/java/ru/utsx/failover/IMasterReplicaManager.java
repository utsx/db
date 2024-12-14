package ru.utsx.failover;

import java.util.Collection;

import ru.utsx.dbs.IMasterDatabase;
import ru.utsx.dbs.IReplicaDatabase;

public interface IMasterReplicaManager<K, V>{

    IMasterDatabase<K, V> getMasterDatabase();

    void switchMaster(IReplicaDatabase<K, V> IReplicaDatabase);

    Collection<IReplicaDatabase<K, V>> getReplicas();

    void addReplica(IReplicaDatabase<K, V> IReplicaDatabase);

    void removeReplica(IReplicaDatabase<K, V> IReplicaDatabase);

}
