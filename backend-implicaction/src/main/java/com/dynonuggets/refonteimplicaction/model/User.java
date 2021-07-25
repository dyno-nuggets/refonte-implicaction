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

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "wp_users")
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @Column(name = "user_login", unique = true)
    @NotBlank(message = "login is required")
    private String login;
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
    private String dispayName;
}
