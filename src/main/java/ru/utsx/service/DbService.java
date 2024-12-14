package ru.utsx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.utsx.dbs.IMasterDatabase;

@Service
@RequiredArgsConstructor
public class DbService {

    private final IMasterDatabase<String, String> masterDatabase;

    public void put(String key, String value) {
        masterDatabase.getStore().put(key, value);
    }

    public String get(String key) {
        return masterDatabase.getStore().get(key);
    }

    public void remove(String key) {
        masterDatabase.getStore().remove(key);
    }

    public Set<String> keys() {
        return masterDatabase.getStore().keys();
    }

    public void clear(){
        masterDatabase.getStore().clear();
    }

    public boolean contains(String key) {
        return masterDatabase.getStore().contains(key);
    }

    public void putAll(Map<String, String> values) {
        masterDatabase.getStore().putAll(values);
    }

}
