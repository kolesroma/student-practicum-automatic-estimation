package com.kpi.kolesnyk.practicum.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class QualityCreationDto {
    @Range(min = 0, max = 100)
    private final Integer caseCoef;

    @Range(min = 0, max = 100)
    private final Integer linterCoef;

    @Range(min = 0, max = 100)
    private final Integer complexityCoef;
}
