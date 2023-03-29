package com.dynonuggets.refonteimplicaction.job.jobapplication.dto;

import com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JobApplicationDto {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String companyImageUri;

    private String statusCode;

    private String location;

    private ContractTypeEnum contractType;

    private boolean archive;

}
