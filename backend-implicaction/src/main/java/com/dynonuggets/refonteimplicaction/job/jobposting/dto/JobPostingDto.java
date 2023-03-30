package com.dynonuggets.refonteimplicaction.job.jobposting.dto;

import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.BusinessSectorEnum;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
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
    private ContractTypeEnum contractType;
    private BusinessSectorEnum businessSector;
    private Instant createdAt;
    private String durationAsString;
    private boolean archive;
    private boolean apply;
    private boolean valid;
    private Long posterId;
    private String posterName;
}
