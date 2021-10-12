package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c from Company c " +
            "where c.name like %:search% or " +
            "c.description like %:search% or " +
            "c.url like %:search%"
    )
    Page<Company> findAllBySearchKey(Pageable pageable, @Param("search") String search);
}
