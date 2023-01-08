package com.kpi.kolesnyk.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "university_group")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<UserEntity> users;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "group_task",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TaskEntity> tasks;

    @ManyToMany(mappedBy = "teacherGroups")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<UserEntity> teachers;
}
