package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

@Data
public class TaskCreationDto {
    private final String name;
    private final String description;
    private final FunctionCreationDto function;
}
