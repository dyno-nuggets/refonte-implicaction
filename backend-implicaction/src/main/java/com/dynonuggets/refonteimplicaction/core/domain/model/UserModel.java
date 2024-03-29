package com.dynonuggets.refonteimplicaction.core.domain.model;

import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "username", unique = true)
    @NotBlank(message = "login is required")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "password is required")
    private String password;

    @Email
    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email is required")
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private LocalDate birthday;

    @Column(name = "registered_at")
    private Instant registeredAt;

    @Column(name = "activation_key")
    private String activationKey;

    @Column
    private boolean enabled;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @ManyToMany(cascade = ALL, fetch = EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<RoleModel> roles;

    public boolean isAdmin() {
        return emptyStreamIfNull(roles).map(RoleModel::getName).anyMatch(RoleEnum.ROLE_ADMIN::equals);
    }
}
