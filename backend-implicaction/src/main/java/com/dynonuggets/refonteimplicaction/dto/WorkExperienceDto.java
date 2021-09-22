package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;


@Data
@Builder
@Getter
@AllArgsConstructor
public class WorkExperienceDto {
    private Long id;
    private UserDto user;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private String label;
    private String description;
    private String companyName;
}
