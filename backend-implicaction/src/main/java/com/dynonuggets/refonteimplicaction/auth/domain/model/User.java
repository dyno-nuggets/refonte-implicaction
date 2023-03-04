package com.dynonuggets.refonteimplicaction.auth.domain.model;

import com.dynonuggets.refonteimplicaction.model.Notification;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.ADMIN;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.emptyStreamIfNull;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_notification",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List<Notification> notifications = new ArrayList<>();

    public boolean isAdmin() {
        return emptyStreamIfNull(roles).anyMatch(role -> ADMIN.name().equals(role.getName()));
    }
}
