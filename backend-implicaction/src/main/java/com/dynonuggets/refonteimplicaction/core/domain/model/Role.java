package com.dynonuggets.refonteimplicaction.core.domain.model;

import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = LAZY, cascade = ALL, mappedBy = "roles")
    private Set<UserModel> users;
}
