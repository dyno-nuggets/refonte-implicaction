package com.dynonuggets.refonteimplicaction.user.domain.model;

import com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private RoleEnum name;
    @ManyToMany(fetch = LAZY, cascade = ALL, mappedBy = "roles")
    private Set<UserModel> users;
}
