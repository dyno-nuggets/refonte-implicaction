package com.dynonuggets.refonteimplicaction.job.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String name;
    private String logo;
    private String description;
    private String url;

}
