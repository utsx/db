package ru.utsx.failover;

import ru.utsx.dbs.IReplicaDatabase;

public interface IFailoverHandler<K, V> {

    void autoFailover();

    boolean checkReplicaStayMaster();

    void onMasterUnavailable();

    void onMasterSwitched(IReplicaDatabase<K, V> newMaster);

}
