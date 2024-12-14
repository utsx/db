package ru.utsx.controllers;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.utsx.dto.DataDto;
import ru.utsx.dto.PutAllDto;
import ru.utsx.dto.PutDto;
import ru.utsx.facade.DbFacade;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DbController {

    private final DbFacade facade;

    @GetMapping(value = "/get")
    public DataDto get(@RequestParam String key) {
        return facade.get(key);
    }

    @PutMapping(value = "/put", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void put(@RequestBody PutDto dto){
        facade.put(dto);
    }

    @DeleteMapping("/clear")
    public void clear() {
        facade.clear();
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam String key) {
        facade.remove(key);
    }

    @GetMapping("/keys")
    public Set<String> keys() {
        return facade.keys();
    }

    @GetMapping("/contains")
    public Boolean contains(@RequestParam String key) {
        return facade.contains(key);
    }

    @PutMapping(value = "/putAll", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void putAll(@RequestBody PutAllDto dto) {
        facade.putAll(dto);
    }

}
