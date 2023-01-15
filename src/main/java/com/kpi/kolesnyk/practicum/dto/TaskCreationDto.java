package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TaskCreationDto {
    @NotEmpty
    private final String name;

    @NotEmpty
    @Size(min = 20)
    private final String description;

    private final FunctionCreationDto function;
    private final QualityCreationDto quality;
    private final List<GroupCreationDto> groups;
}
