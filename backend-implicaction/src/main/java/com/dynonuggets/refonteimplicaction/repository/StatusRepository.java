package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends JpaRepository<Status, Long> {

    @Query("select s from Status s " +
            "where (s.id= 2 and s.type = 'job_posting')")
    Status getArchivedStatus();

    @Query("select s from Status s " +
            "where (s.id= 1 and s.type = 'job_posting')")
    Status getUnarchivedStatus();
}
