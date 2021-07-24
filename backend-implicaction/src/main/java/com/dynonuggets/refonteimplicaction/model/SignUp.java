package com.dynonuggets.refonteimplicaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "wp_signups")
public class SignUp {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "signup_id")
    private Long signupId;
    private String domain;
    private String path;
    private String title;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_email")
    private String userEmail;
    private Instant registered;
    private Instant activated;
    private Boolean active;
    @Column(name = "activation_key")
    private String activationKey;
    private String meta;
}
