package com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository;

import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPostingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingModel, Long>, JobPostingRepositoryCustom {

    Page<JobPostingModel> findAllByValidIsFalse(Pageable pageable);

    Page<JobPostingModel> findAllByArchiveFalseAndValidTrueOrderByCreatedAtDesc(Pageable pageable);
}
