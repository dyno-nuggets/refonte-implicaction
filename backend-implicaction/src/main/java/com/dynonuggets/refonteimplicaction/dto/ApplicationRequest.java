package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ApplicationRequest {
    private Long jobId;
    private ApplyStatusEnum status;
}
