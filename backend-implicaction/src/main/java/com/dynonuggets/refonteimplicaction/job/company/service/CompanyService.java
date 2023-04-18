package com.dynonuggets.refonteimplicaction.job.company.service;

import com.dynonuggets.refonteimplicaction.job.company.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.job.company.domain.model.CompanyModel;
import com.dynonuggets.refonteimplicaction.job.company.domain.repository.CompanyRepository;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyAdapter companyAdapter;

    /**
     * @return la liste paginée des entreprises
     */
    public Page<CompanyDto> getAll(final Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyAdapter::toDto);
    }

    public Page<CompanyDto> getAllWithCriteria(final Pageable pageable, final String keyword) {
        return companyRepository.findAllWithCriteria(pageable, keyword)
                .map(companyAdapter::toDto);
    }

    @Transactional
    public CompanyDto saveOrUpdateCompany(final CompanyDto companyDto) {
        final CompanyModel company = companyAdapter.toModel(companyDto);
        final CompanyModel saved = companyRepository.save(company);
        return companyAdapter.toDto(saved);
    }
}
