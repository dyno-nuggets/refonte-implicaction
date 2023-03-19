package com.dynonuggets.refonteimplicaction.core.user.domain.model;

import com.dynonuggets.refonteimplicaction.core.user.domain.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.emptyStreamIfNull;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
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

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @Column(name = "registered_at")
    private Instant registeredAt;

    @Column(name = "activation_key")
    private String activationKey;

    // TODO: modifier en enabled
    private boolean active;
    private boolean emailVerified;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    public boolean isAdmin() {
        return emptyStreamIfNull(roles).anyMatch(role -> RoleEnum.ADMIN.name().equals(role.getName()));
    }
}
