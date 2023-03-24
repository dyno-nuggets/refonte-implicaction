package com.dynonuggets.refonteimplicaction.community.relation.dto;

import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class RelationsDto {
    private Long id;
    private ProfileDto sender;
    private ProfileDto receiver;
    private Instant sentAt;
    private Instant confirmedAt;
    @Setter
    private RelationTypeEnum relationType;
}
