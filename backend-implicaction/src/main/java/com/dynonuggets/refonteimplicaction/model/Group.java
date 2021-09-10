package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User creator;
    private String description;
    @Column(name = "created_at")
    private Instant createdAt;

}
