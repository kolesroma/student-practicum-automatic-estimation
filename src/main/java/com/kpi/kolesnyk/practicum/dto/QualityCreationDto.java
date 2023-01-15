package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;

@Data
public class QualityCreationDto {
    private final Integer caseCoef;
    private final Integer linterCoef;
    private final Integer complexityCoef;
}
