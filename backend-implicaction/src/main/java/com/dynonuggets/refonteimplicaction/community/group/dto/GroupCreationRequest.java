package com.dynonuggets.refonteimplicaction.community.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class GroupCreationRequest {
    @NotBlank(message = "Le nom d'un groupe ne peut pas être vide")
    private String name;
    private String description;
}
