package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RelationsDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private LocalDate date;
}
