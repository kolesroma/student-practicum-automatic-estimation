package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class FunctionCreationDto {
    private final String name;
    private final List<ParamCreationDto> params;
    private final List<InputCreationDto> inputs;
}
