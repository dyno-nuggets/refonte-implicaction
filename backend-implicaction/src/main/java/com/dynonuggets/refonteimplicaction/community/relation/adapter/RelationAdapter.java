package com.dynonuggets.refonteimplicaction.community.relation.adapter;

import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.community.relation.domain.model.RelationModel;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RelationAdapter {

    private final ProfileAdapter profileAdapter;

    public RelationsDto toDto(final RelationModel relation) {
        return RelationsDto.builder()
                .id(relation.getId())
                .sender(profileAdapter.toDtoLight(relation.getSender()))
                .receiver(profileAdapter.toDtoLight(relation.getReceiver()))
                .sentAt(relation.getSentAt())
                .confirmedAt(relation.getConfirmedAt())
                .build();
    }
}
