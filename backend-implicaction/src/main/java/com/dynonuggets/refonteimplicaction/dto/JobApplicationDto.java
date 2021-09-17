package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JobApplicationDto {
    
    private Long id;

    private JobPostingDto jobPosting;

    private UserDto user;

    private ApplicationStatusDto status;

}
