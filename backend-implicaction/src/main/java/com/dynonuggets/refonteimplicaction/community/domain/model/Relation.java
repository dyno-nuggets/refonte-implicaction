package com.dynonuggets.refonteimplicaction.community.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "relation")
public class Relation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Profile sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Profile receiver;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "confirmed_at")
    private Instant confirmedAt;
}
