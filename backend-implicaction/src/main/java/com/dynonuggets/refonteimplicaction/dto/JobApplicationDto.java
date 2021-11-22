package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum;
import com.dynonuggets.refonteimplicaction.model.ContractTypeEnum;
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

    private ApplyStatusEnum status;

    private ContractTypeEnum contractType;

}
