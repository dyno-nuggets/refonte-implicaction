package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.auth.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Relation;
import com.dynonuggets.refonteimplicaction.community.rest.dto.RelationsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RelationAdapter {

    private final UserAdapter userAdapter;

    public RelationsDto toDto(final Relation relation) {
        return RelationsDto.builder()
                .id(relation.getId())
                .sender(userAdapter.toDto(relation.getSender()))
                .receiver(userAdapter.toDto(relation.getReceiver()))
                .sentAt(relation.getSentAt())
                .confirmedAt(relation.getConfirmedAt())
                .build();
    }
}
