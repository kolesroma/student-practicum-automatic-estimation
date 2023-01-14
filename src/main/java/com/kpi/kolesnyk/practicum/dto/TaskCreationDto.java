package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskCreationDto {
    private final String name;
    private final String description;
    private final FunctionCreationDto function;
    private final QualityCreationDto quality;
    private final List<GroupCreationDto> groups;
}
