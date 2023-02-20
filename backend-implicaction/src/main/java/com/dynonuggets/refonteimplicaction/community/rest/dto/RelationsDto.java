package com.dynonuggets.refonteimplicaction.community.rest.dto;

import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class RelationsDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private Instant sentAt;
    private Instant confirmedAt;
}
