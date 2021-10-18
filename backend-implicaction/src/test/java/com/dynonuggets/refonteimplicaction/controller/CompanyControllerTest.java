package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.CompanyService;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest {

    @Autowired
    protected MockMvc mvc;
    List<CompanyDto> companyDtos;
    @InjectMocks
    private CompanyController companyController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private CompanyService companyService;

    @BeforeEach
    protected void setUp() {
        companyDtos = Arrays.asList(
                CompanyDto.builder().id(1L).description("description").name("Idemia").logo("logo").url("url").build(),
                CompanyDto.builder().id(2L).description("description2").name("SG").logo("logo2").url("url2").build());
    }

    @WithMockUser
    @Test
    void getCompanysListShouldListAllCompanies() throws Exception {

        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";

        Page<CompanyDto> companyDtoPage = new PageImpl<>(companyDtos);
        Pageable pageable = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
        ResultActions actions;

        when(companyService.getAll(pageable)).thenReturn(companyDtoPage);
        actions = mvc.perform(MockMvcRequestBuilders.get("/api/companies").accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalPages").value(companyDtoPage.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(companyDtos.size()));

        for (int i = 0; i < companyDtos.size(); i++) {
            actions.andExpect(jsonPath("$.content[" + i + "].id", Matchers.is(Math.toIntExact(companyDtos.get(i).getId()))))
                    .andExpect(jsonPath("$.content[" + i + "].description", Matchers.is(companyDtos.get(i).getDescription())))
                    .andExpect(jsonPath("$.content[" + i + "].name", Matchers.is(companyDtos.get(i).getName())))
                    .andExpect(jsonPath("$.content[" + i + "].logo", Matchers.is(companyDtos.get(i).getLogo())))
                    .andExpect(jsonPath("$.content[" + i + "].url", Matchers.is(companyDtos.get(i).getUrl())));
        }
        actions.andReturn();
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";

        Pageable pageable = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));

        when(companyService.getAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mvc.perform(MockMvcRequestBuilders.get("/api/companies")
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }
}