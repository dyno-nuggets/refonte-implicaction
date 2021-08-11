package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "wp_signups")
public class Signup {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "signup_id")
    private Long signupId;
    private String domain;
    private String path;
    private String title;
    @Column(name = "user_login", unique = true)
    @NotBlank(message = "username is required")
    private String username;
    @Column(name = "user_email", unique = true)
    @NotEmpty(message = "Email is required")
    private String userEmail;
    private Instant registered;
    private Instant activated;
    private Boolean active;
    @Column(name = "activation_key")
    @NotEmpty(message = "Activation Key is required")
    private String activationKey;
    private String meta;
}
