package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class InputCreationDto {
    private final List<String> inputParamValues;
    private final String expectedResult;
}
