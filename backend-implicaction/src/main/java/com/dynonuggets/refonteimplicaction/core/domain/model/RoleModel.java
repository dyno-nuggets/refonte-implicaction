package com.dynonuggets.refonteimplicaction.core.domain.model;

import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
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
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = LAZY, cascade = ALL, mappedBy = "roles")
    private Set<UserModel> users;
}
