package com.dynonuggets.refonteimplicaction.community.relation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RelationCreationRequest {
    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;
}
