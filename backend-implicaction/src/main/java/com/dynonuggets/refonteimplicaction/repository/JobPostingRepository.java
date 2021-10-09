package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    @Query("select j from JobPosting j join j.company c " +
            "where j.description like %:search% or " +
            "j.keywords like %:search% or " +
            "j.location like %:search% or " +
            "j.title like %:search% or " +
            "c.name like %:search%"
    )
    Page<JobPosting> findAllBySearchKey(Pageable pageable, @Param("search") String search);
}
