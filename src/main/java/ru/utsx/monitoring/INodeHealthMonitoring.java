package ru.utsx.monitoring;

import java.util.Map;

import ru.utsx.dbs.IReplicaDatabase;

public interface INodeHealthMonitoring {


    boolean isMasterAvailable();

    boolean isReplicaAvailable(IReplicaDatabase<?, ?> replica);

    Map<String, Boolean> getNodeStatuses();
}
