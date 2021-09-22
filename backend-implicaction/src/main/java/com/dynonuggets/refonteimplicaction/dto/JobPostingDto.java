package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.model.ContractType;
import com.dynonuggets.refonteimplicaction.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class JobPostingDto {

    private Long id;
    private CompanyDto company;
    private String title;
    private String description;
    private String location;
    private String salary;
    private String keywords;
    private ContractType contractType;
    private Status status;

}
