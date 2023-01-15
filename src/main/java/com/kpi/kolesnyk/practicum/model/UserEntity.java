package com.kpi.kolesnyk.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 4)
    private String username;

    @Size(min = 6)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @Email
    private String email;

    private LocalDate birthday;

    @OneToOne()
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "teacher_group",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GroupEntity> teacherGroups;
}
