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
public class TrainingDto {
    private Long id;
    private String label;
    private LocalDate date;
    private String school;
}
