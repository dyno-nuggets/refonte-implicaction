package com.dynonuggets.refonteimplicaction.job.company.domain.repository;

import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long>, CompanyRepositoryCustom {
}
