package com.dynonuggets.refonteimplicaction.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "email", unique = true)
    @NotEmpty(message = "e-mail is required")
    @Email
    private String email;

    private String firstname;

    private String lastname;

    private LocalDate birthday;

    private String url;

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
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
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
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.LAZY)
      @JoinTable(name = "user_chat_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_room_id")})
    private List<ChatRoom> chatRooms;

}
