package com.kpi.kolesnyk.practicum.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
public class TaskEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "task")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FunctionEntity function;

    private String name;

    private String description;

    private LocalDateTime createdAt;
}
