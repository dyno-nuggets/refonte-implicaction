package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.CompanyAdapter;
import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import com.dynonuggets.refonteimplicaction.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    CompanyAdapter companyAdapter;

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_list_all_companies() {
        // given
        final List<Company> companies = Collections.singletonList(new Company(1L, "Idemia", "logo", "description", "url"));
        Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
        Page<Company> expectedPages = new PageImpl<>(companies);

        CompanyDto companyDto1 = new CompanyDto(1L, "Idemia", "logo", "description", "url");

        given(companyAdapter.toDto(any())).willReturn(companyDto1);
        given(companyRepository.findAll(any(Pageable.class))).willReturn(expectedPages);

        // when
        Page<CompanyDto> actualPages = companyService.getAll(pageable);

        // then
        assertThat(actualPages.getSize()).isEqualTo(expectedPages.getSize());
        verify(companyRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void should_create_a_company() {
        //given
        Company company = Company.builder()
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        Company companySaved = Company.builder()
                .id(1L)
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        CompanyDto expectedDto = CompanyDto.builder()
                .id(1L)
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        CompanyDto sentDto = CompanyDto.builder()
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        given(companyAdapter.toModel(any())).willReturn(company);
        given(companyRepository.save(any())).willReturn(companySaved);
        given(companyAdapter.toDto(any())).willReturn(expectedDto);

        //when
        CompanyDto actualDto = companyService.saveOrUpdateCompany(sentDto);

        //then
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
        verify(companyRepository, times(1)).save(any());

    }

    @Test
    void should_update_a_company() {
        //given
        Company company = Company.builder()
                .id(4L)
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        Company companySaved = Company.builder()
                .id(4L)
                .description("Compani Test service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        CompanyDto expectedDto = CompanyDto.builder()
                .id(4L)
                .description("Compani Test update service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        CompanyDto sentDto = CompanyDto.builder()
                .id(4L)
                .description("Compani Test update service")
                .name("Idemia")
                .url("url")
                .logo("logoo")
                .build();

        given(companyAdapter.toModel(any())).willReturn(company);
        given(companyRepository.save(any())).willReturn(companySaved);
        given(companyAdapter.toDto(any())).willReturn(expectedDto);

        //when
        CompanyDto actualDto = companyService.saveOrUpdateCompany(sentDto);

        //then
        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
        verify(companyRepository, times(1)).save(any());

    }
}
