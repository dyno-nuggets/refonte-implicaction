package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "signup")
public class Signup {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "signup_id")
    private Long signupId;
    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String title;
    private String domain;
    private String path;
    private Instant registered;
    private Instant activated;
    private Boolean active;
    @Column(name = "activation_key")
    @NotEmpty(message = "Activation Key is required")
    private String activationKey;
    private String meta;
}
