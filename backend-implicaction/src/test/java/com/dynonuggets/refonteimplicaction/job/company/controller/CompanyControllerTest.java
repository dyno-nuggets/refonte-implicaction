package com.dynonuggets.refonteimplicaction.job.company.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.job.company.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.job.company.service.CompanyService;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.job.company.utils.CompanyUris.COMPANIES_BASE_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = COMPANIES_BASE_URI;

    List<CompanyDto> companyDtos;

    @MockBean
    CompanyService companyService;

    @BeforeEach
    protected void setUp() {
        companyDtos = Arrays.asList(
                CompanyDto.builder().id(1L).description("Société").name("Idemia").logo("logo").url("url").build(),
                CompanyDto.builder().id(2L).description("description2").name("Société générale").logo("logo2").url("url2").build());

    }

    @WithMockUser
    @Test
    void getCompanysListShouldListAllCompanies() throws Exception {
        // given
        final Page<CompanyDto> companyDtoPage = new PageImpl<>(companyDtos);
        given(companyService.getAllWithCriteria(any(), anyString())).willReturn(companyDtoPage);

        // when
        final ResultActions resultActions = mvc.perform(get(baseUri)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(companyDtoPage.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(companyDtos.size()));

        // test des propriétés de chaque éléments de la liste reçue
        for (int i = 0; i < companyDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(companyDtos.get(i).getId()))))
                    .andExpect(jsonPath(contentPath + ".description", is(companyDtos.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".name", is(companyDtos.get(i).getName())))
                    .andExpect(jsonPath(contentPath + ".logo", is(companyDtos.get(i).getLogo())))
                    .andExpect(jsonPath(contentPath + ".url", is(companyDtos.get(i).getUrl())));
        }

        verify(companyService, times(1)).getAllWithCriteria(any(), anyString());
    }

    @WithMockUser
    @Test
    void getCompaniesListShouldListAllCompaniesByCriteria() throws Exception {
        // given
        final Page<CompanyDto> companyDtoPage = new PageImpl<>(companyDtos);

        // when
        // test des données de pagination
        given(companyService.getAllWithCriteria(any(), anyString())).willReturn(companyDtoPage);
        final ResultActions actions = mvc.perform(get(baseUri)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk());

        // test des propriétés de chaque éléments de la liste reçue
        for (int i = 0; i < companyDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(companyDtos.get(i).getId()))))
                    .andExpect(jsonPath(contentPath + ".description", is(companyDtos.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".name", is(companyDtos.get(i).getName())))
                    .andExpect(jsonPath(contentPath + ".logo", is(companyDtos.get(i).getLogo())))
                    .andExpect(jsonPath(contentPath + ".url", is(companyDtos.get(i).getUrl())));
        }
        verify(companyService, times(1)).getAllWithCriteria(any(), anyString());
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(baseUri)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verify(companyService, never()).getAll(any());
    }

    @Test
    @WithMockUser
    void should_create_company_when_authenticated() throws Exception {
        // given
        final CompanyDto companyDto = CompanyDto.builder()
                .id(123L)
                .description("Description")
                .name("Implicaction")
                .url("url")
                .build();

        final String json = gson.toJson(companyDto);

        final CompanyDto expectedDto = CompanyDto.builder()
                .id(123L)
                .description("Description")
                .name("Implicaction")
                .url("url")
                .build();
        given(companyService.saveOrUpdateCompany(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.description", is(expectedDto.getDescription())))
                .andExpect(jsonPath("$.name", is(expectedDto.getName())))
                .andExpect(jsonPath("$.url", is(expectedDto.getUrl())));
        verify(companyService, times(1)).saveOrUpdateCompany(any());
    }

    @Test
    void should_not_create_company_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        final CompanyDto companyDto = CompanyDto.builder()
                .description("Description")
                .name("Implicaction")
                .url("url")
                .build();

        final String json = gson.toJson(companyDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(companyService, never()).saveOrUpdateCompany(any());
    }
}
