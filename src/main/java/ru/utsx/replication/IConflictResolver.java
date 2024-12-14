package ru.utsx.replication;

public interface IConflictResolver<K, V> {

    V resolveConflict(K key, V masterValue, V replicaValue);

}
