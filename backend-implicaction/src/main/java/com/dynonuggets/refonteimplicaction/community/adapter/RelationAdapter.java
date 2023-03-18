package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.community.domain.model.Relation;
import com.dynonuggets.refonteimplicaction.community.dto.RelationsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RelationAdapter {

    private final ProfileAdapter profileAdapter;

    public RelationsDto toDto(final Relation relation) {
        return RelationsDto.builder()
                .id(relation.getId())
                .sender(profileAdapter.toDtoLight(relation.getSender()))
                .receiver(profileAdapter.toDtoLight(relation.getReceiver()))
                .sentAt(relation.getSentAt())
                .confirmedAt(relation.getConfirmedAt())
                .build();
    }
}
