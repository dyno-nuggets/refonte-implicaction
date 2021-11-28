package com.dynonuggets.refonteimplicaction.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User user;

    private Instant date;

    @Column(name = "is_read")
    private boolean read;

    @Column(name = "is_sent")
    private boolean sent;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "notifications")
    private List<User> users;
}
