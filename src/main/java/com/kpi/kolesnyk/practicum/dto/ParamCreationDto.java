package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ParamCreationDto {
    @NotEmpty
    private final String name;

    @NotEmpty
    private final String type;
}
