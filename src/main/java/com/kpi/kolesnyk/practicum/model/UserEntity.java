package com.kpi.kolesnyk.practicum.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    private String username;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String password;

    @OneToOne()
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
