package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_login", unique = true)
    @NotBlank(message = "login is required")
    private String username;
    @Column(name = "user_pass")
    @NotBlank(message = "password is required")
    private String password;
    @Column(name = "user_nicename")
    private String nicename;
    @Column(name = "user_email", unique = true)
    @NotEmpty(message = "Email is required")
    @Email
    private String email;
    @Column(name = "user_url")
    private String url;
    @Column(name = "user_registered")
    private Instant registered;
    @Column(name = "user_activation_key")
    private String activationKey;
    @Column(name = "user_status")
    private Integer status;
    @Column(name = "display_name")
    private String displayName;
    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<WorkExperience> experiences;
    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<Training> trainings;
    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, updatable = false)
            })
    private Set<Group> groups = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sender", cascade = ALL, orphanRemoval = true)
    private Set<Relation> relationsAsSender;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reciever", cascade = ALL, orphanRemoval = true)
    private Set<Relation> relationsAsReciever;
    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = ALL)
    private Signup signup;
    @Column(name = "phone_number")
    private String phoneNumber;
    private LocalDate birthday;
    private String hobbies;
}
