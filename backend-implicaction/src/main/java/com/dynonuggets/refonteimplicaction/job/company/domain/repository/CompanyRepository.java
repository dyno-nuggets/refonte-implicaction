package com.dynonuggets.refonteimplicaction.job.company.domain.repository;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
}
