package com.dynonuggets.refonteimplicaction.auth.domain.model;

import com.dynonuggets.refonteimplicaction.model.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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

    private String firstname;

    private String lastname;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    private String url;

    private LocalDate birthday;

    private String hobbies;

    private String purpose;

    private String presentation;

    private String expectation;

    private String contribution;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registered_at")
    private Instant registeredAt;

    @Column(name = "activated_at")
    private Instant activatedAt;

    @Column(name = "activation_key")
    private String activationKey;

    private boolean active;

    @ManyToOne
    private FileModel image;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_notification",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<WorkExperience> experiences;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<Training> trainings;
}
