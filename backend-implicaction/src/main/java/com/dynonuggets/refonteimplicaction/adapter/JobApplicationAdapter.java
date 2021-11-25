package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.model.JobApplication;
import com.dynonuggets.refonteimplicaction.model.JobPosting;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationAdapter {

    public JobApplicationDto toDto(JobApplication model) {
        final JobPosting job = model.getJob();
        final boolean hasCompany = job.getCompany() != null;
        final String companyName = hasCompany ? job.getCompany().getName() : null;
        final String companyImageUrl = hasCompany ? job.getCompany().getLogo() : null;

        return JobApplicationDto.builder()
                .id(model.getId())
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .companyName(companyName)
                .companyImageUri(companyImageUrl)
                .location(job.getLocation())
                .statusCode(model.getStatus().name())
                .contractType(job.getContractType())
                .archive(model.isArchive())
                .build();
    }
}
