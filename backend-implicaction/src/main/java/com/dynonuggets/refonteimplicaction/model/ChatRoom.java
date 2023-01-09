package com.dynonuggets.refonteimplicaction.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank (message = "Name must not be blank")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "is_aggregation")
    private boolean isAggregation;

    @Column(name = "last_ID_message")
    private String lastIdMessage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "chatRooms")
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL, mappedBy = "chatRooms")
    private List<Group> groups;
}
