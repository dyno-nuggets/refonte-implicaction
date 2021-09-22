package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyAdapterTest {
    Company company;
    CompanyDto companyDto;
    CompanyAdapter companyAdapter;

    @BeforeEach
    public void SetUp() {
        companyAdapter = new CompanyAdapter();

        company = Company.builder()
                .id(1L)
                .url("urlModel")
                .logo("logo")
                .name("name")
                .description("description")
                .build();

        companyDto = CompanyDto.builder()
                .id(2L)
                .url("url")
                .logo("logo")
                .name("name")
                .description("description")
                .build();
    }

    @Test
    void toDtoTest() {
        CompanyDto companyDtoTest = companyAdapter.toDto(company);

        assertThat(companyDtoTest.getId()).isEqualTo(company.getId());
        assertThat(companyDtoTest.getName()).isEqualTo(company.getName());
        assertThat(companyDtoTest.getDescription()).isEqualTo(company.getDescription());
        assertThat(companyDtoTest.getLogo()).isEqualTo(company.getLogo());
        assertThat(companyDtoTest.getUrl()).isEqualTo(company.getUrl());

    }

    @Test
    void toModelTest() {
        Company companyTest = companyAdapter.toModel(companyDto);

        assertThat(companyTest.getId()).isEqualTo(companyDto.getId());
        assertThat(companyTest.getName()).isEqualTo(companyDto.getName());
        assertThat(companyTest.getDescription()).isEqualTo(companyDto.getDescription());
        assertThat(companyTest.getLogo()).isEqualTo(companyDto.getLogo());
        assertThat(companyTest.getUrl()).isEqualTo(companyDto.getUrl());
    }
}
