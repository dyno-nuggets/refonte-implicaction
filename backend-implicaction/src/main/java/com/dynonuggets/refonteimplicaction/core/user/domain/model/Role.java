package com.dynonuggets.refonteimplicaction.core.user.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "roles")
    private Set<User> users;
}
