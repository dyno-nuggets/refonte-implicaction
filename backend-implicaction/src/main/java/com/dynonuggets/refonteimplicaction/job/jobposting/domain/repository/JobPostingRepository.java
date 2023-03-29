package com.dynonuggets.refonteimplicaction.job.jobposting.domain.repository;

import com.dynonuggets.refonteimplicaction.job.jobposting.domain.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>, JobPostingRepositoryCustom {

    Page<JobPosting> findAllByValidIsFalse(Pageable pageable);

    Page<JobPosting> findAllByArchiveFalseAndValidTrueOrderByCreatedAtDesc(Pageable pageable);
}
