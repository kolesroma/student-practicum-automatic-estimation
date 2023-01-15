package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class InputCreationDto {
    @Size(min = 1)
    private final List<String> inputParamValues;
    private final String expectedResult;
}
