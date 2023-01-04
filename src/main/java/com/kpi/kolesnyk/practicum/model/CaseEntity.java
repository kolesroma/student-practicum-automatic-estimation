package com.kpi.kolesnyk.practicum.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cases")
@Getter
@Setter
@ToString
public class CaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "param_id")
    private ParamEntity param;

    @Column(name = "val")
    private String value;

    @OneToOne
    @JoinColumn(name = "result_id")
    private ResultEntity result;
}
