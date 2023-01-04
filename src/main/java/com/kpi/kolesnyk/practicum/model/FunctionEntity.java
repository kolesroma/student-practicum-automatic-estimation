package com.kpi.kolesnyk.practicum.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "function")
@Getter
@Setter
@ToString
public class FunctionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @OneToMany(mappedBy = "function")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ParamEntity> params;

    private String name;
}
