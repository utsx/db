package ru.utsx.dbs;

import java.util.stream.Collectors;

import lombok.Getter;

import ru.utsx.core.IKeyValueStore;

public record ReplicaDatabase<K, V>(IMasterDatabase<K, V> master,
                                    IKeyValueStore<K, V> store) implements IReplicaDatabase<K, V> {

    public ReplicaDatabase(IMasterDatabase<K, V> master, IKeyValueStore<K, V> store) {
        this.master = master;
        this.store = store;
        catchingUpMaster();
    }

    private void catchingUpMaster() {
        store.putAll(master.getStore().keys().stream()
                .collect(Collectors.toMap(k -> k, master.getStore()::get)));
    }

}
