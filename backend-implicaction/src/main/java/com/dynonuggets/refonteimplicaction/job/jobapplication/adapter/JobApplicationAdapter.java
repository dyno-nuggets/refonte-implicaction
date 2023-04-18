package com.dynonuggets.refonteimplicaction.job.jobapplication.adapter;

import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.model.JobApplicationModel;
import com.dynonuggets.refonteimplicaction.job.jobapplication.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationAdapter {

    public JobApplicationDto toDto(final JobApplicationModel model) {
        final JobPostingModel job = model.getJob();
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
