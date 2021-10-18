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

    private final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
    private final String BASE_URI = "/api/companies";

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

        Page<CompanyDto> companyDtoPage = new PageImpl<>(companyDtos);
        ResultActions actions;

        // test des données de pagination
        when(companyService.getAll(DEFAULT_PAGEABLE)).thenReturn(companyDtoPage);
        actions = mvc.perform(MockMvcRequestBuilders.get(BASE_URI).accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalPages").value(companyDtoPage.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(companyDtos.size()));

        // test des propriétés de chaque éléments de la liste reçue
        for (int i = 0; i < companyDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", Matchers.is(Math.toIntExact(companyDtos.get(i).getId()))))
                    .andExpect(jsonPath(contentPath + ".description", Matchers.is(companyDtos.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".name", Matchers.is(companyDtos.get(i).getName())))
                    .andExpect(jsonPath(contentPath + ".logo", Matchers.is(companyDtos.get(i).getLogo())))
                    .andExpect(jsonPath(contentPath + ".url", Matchers.is(companyDtos.get(i).getUrl())));
        }
        actions.andReturn();
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {

        when(companyService.getAll(DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI)
                        .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }
}
