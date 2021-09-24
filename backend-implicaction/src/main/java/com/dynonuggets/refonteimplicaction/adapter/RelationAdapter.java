package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.model.Relation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RelationAdapter {

    private final UserAdapter userAdapter;

    public RelationsDto toDto(Relation relation) {
        return RelationsDto.builder()
                .id(relation.getId())
                .sender(userAdapter.toDto(relation.getSender()))
                .receiver(userAdapter.toDto(relation.getReceiver()))
                .sentAt(relation.getSentAt())
                .confirmedAt(relation.getConfirmedAt())
                .build();
    }
}
