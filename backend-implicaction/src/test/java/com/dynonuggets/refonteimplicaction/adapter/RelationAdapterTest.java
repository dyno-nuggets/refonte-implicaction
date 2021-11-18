package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RelationAdapterTest {

    @Mock
    WorkExperienceAdapter workExperienceAdapter;

    @Mock
    TrainingAdapter trainingAdapter;

    @Mock
    CompanyAdapter companyAdapter;

    @InjectMocks
    UserAdapter userAdapter;

    RelationAdapter relationAdapter;

    User receiver;
    User sender;
    Relation relation;

    @BeforeEach
    public void setUp() {
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

        relationAdapter = new RelationAdapter(userAdapter);
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
