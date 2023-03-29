package com.dynonuggets.refonteimplicaction.job.jobapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JobApplicationRequest {
    private Long jobId;
    private ApplyStatusEnum status;
    private Boolean archive;
}
