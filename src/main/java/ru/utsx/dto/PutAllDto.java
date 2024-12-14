package ru.utsx.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutAllDto {
    @JsonProperty("map")
    Map<String, String> keyValuesMap;

}
