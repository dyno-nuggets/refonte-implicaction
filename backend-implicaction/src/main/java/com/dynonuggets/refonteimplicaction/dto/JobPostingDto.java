package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Data
@Builder
@Getter
@AllArgsConstructor
public class JobPostingDto {

    private Long id;
    private CompanyDto company;
    private String title;
    private String shortDescription;
    private String description;
    private String location;
    private String salary;
    private String keywords;
    private ContractTypeDto contractType;
    private StatusDto status;
    private Instant createdAt;

}
