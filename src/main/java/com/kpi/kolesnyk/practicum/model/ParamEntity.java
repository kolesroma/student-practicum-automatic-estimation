package com.kpi.kolesnyk.practicum.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "param")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private FunctionEntity function;

    @OneToMany(mappedBy = "param")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CaseEntity> cases;

    private String type;

    private String name;
}
