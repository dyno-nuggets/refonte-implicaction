package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyAdapter companyAdapter;

    /**
     * @return la liste paginée des entreprises
     */
    public Page<CompanyDto> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyAdapter::toDto);
    }
}
