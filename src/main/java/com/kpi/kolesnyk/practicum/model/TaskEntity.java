package com.kpi.kolesnyk.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToOne(mappedBy = "task")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FunctionEntity function;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "tasks")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GroupEntity> groups;
}
