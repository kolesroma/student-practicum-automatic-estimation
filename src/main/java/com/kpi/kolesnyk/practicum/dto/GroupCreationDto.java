package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GroupCreationDto {
    @NotNull
    private Long id;
}
