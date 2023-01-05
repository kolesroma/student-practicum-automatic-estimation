package com.kpi.kolesnyk.practicum.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "result")
@Getter
@Setter
@ToString
public class ResultEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String expected;
}
