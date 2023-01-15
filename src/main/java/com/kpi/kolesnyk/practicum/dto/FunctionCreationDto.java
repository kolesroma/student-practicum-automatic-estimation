package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class FunctionCreationDto {
    @NotEmpty
    private final String name;
    private final List<ParamCreationDto> params;
    private final List<InputCreationDto> inputs;
}
