package ru.utsx.facade;


import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.utsx.dto.DataDto;
import ru.utsx.dto.PutAllDto;
import ru.utsx.dto.PutDto;
import ru.utsx.service.DbService;

@Component
@Slf4j
@RequiredArgsConstructor
public class DbFacade {

    private final DbService service;

    public DataDto get(String key) {
        return DataDto.builder()
                .value(service.get(key))
                .build();
    }

    public void put(PutDto dto) {
        service.put(dto.getKey(), dto.getValue());
    }

    public void remove(String key) {
        service.remove(key);
    }

    public Set<String> keys() {
        return service.keys();
    }

    public boolean contains(String key) {
        return service.contains(key);
    }

    public void clear() {
        service.clear();
    }

    public void putAll(PutAllDto dto) {
        service.putAll(dto.getKeyValuesMap());
    }

}
