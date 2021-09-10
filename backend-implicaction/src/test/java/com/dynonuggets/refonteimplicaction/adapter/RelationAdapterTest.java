package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class RelationAdapterTest {

    RelationAdapter relationAdapter;
    UserAdapter userAdapter;
    User receiver;
    User sender;
    Relation relation;

    @BeforeEach
    public void setUp() {
        userAdapter = new UserAdapter(new WorkExperienceAdapter(), new TrainingAdapter());
        relationAdapter = new RelationAdapter(userAdapter);
        receiver = User.builder()
                .username("user1")
                .build();
        sender = User.builder()
                .username("user2")
                .build();
        relation = Relation.builder()
                .receiver(receiver)
                .sender(sender)
                .confirmedAt(Instant.now())
                .build();
    }

    @Test
    void toDtoTest() {
        RelationsDto dto = relationAdapter.toDto(relation);
        assertThat(dto.getConfirmedAt()).isEqualTo(relation.getConfirmedAt());
        assertThat(dto.getSentAt()).isEqualTo(relation.getSentAt());
        assertThat(dto.getSender().getUsername()).isEqualTo(sender.getUsername());
        assertThat(dto.getReceiver().getUsername()).isEqualTo(receiver.getUsername());
    }
}
