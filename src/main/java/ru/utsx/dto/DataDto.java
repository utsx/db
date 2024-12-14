package ru.utsx.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonRootName("data")
public class DataDto {
    private String value;
}
