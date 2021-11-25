package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobApplicationRequest {
    private Long jobId;
    private ApplyStatusEnum status;
    private Boolean archive;
}
