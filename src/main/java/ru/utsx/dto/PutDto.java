package ru.utsx.dto;

import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutDto {
    private String key;
    private String value;
}
