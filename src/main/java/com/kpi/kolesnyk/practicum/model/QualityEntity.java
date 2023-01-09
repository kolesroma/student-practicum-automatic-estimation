package com.kpi.kolesnyk.practicum.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quality")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Integer caseCoef;

    private Integer linterCoef;

    private Integer complexityCoef;

    @OneToOne
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TaskEntity task;
}
