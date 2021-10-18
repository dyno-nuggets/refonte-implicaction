package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.JobPostingService;
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

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = JobPostingController.class)
class JobsPostingControllerTest {

    private final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
    private final String BASE_URI = "/api/job-postings";

    @Autowired
    private MockMvc mvc;
    @InjectMocks
    private JobPostingController jobPostingController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private JobPostingService jobPostingService;

    private List<JobPostingDto> jobPostings;

    @BeforeEach
    protected void setUp() {
        jobPostings = Arrays.asList(
                JobPostingDto.builder().id(1L).createdAt(Instant.now()).description("description").location("Paris").keywords("toto,yoyo").salary("1111$").build(),
                JobPostingDto.builder().id(2L).createdAt(Instant.now()).description("description2").location("New York").keywords("toto2,yoyo2").salary("2222$").build());
    }

    @WithMockUser
    @Test
    void getJobPostingsListShouldListAllJobs() throws Exception {

        String search = "";
        String contractType = "";

        Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);
        ResultActions actions;

        when(jobPostingService.findAllWithCriteria(DEFAULT_PAGEABLE, search, contractType)).thenReturn(jobPostingPageMockResponse);
        actions = mvc.perform(MockMvcRequestBuilders.get(BASE_URI).accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalPages").value(jobPostingPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(jobPostings.size()));

        for (int i = 0; i < jobPostings.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", Matchers.is(Math.toIntExact(jobPostings.get(i).getId()))))
                    .andExpect(jsonPath(contentPath + ".createdAt", Matchers.is(jobPostings.get(i).getCreatedAt().toString())))
                    .andExpect(jsonPath(contentPath + ".description", Matchers.is(jobPostings.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".location", Matchers.is(jobPostings.get(i).getLocation())))
                    .andExpect(jsonPath(contentPath + ".keywords", Matchers.is(jobPostings.get(i).getKeywords())))
                    .andExpect(jsonPath(contentPath + ".salary", Matchers.is(jobPostings.get(i).getSalary())));
        }
        actions.andReturn();
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";
        String search = "";
        String contractType = "";

        Pageable DEFAULT_PAGEABLE = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));

        when(jobPostingService.findAllWithCriteria(DEFAULT_PAGEABLE, search, contractType)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI)
                        .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser
    void getJobByIdShouldReturnOneJob() throws Exception {

        JobPostingDto jobPostingDto = JobPostingDto.builder()
                .id(1L)
                .createdAt(Instant.now())
                .description("description")
                .location("Paris")
                .keywords("toto,yoyo")
                .salary("1111$")
                .build();

        when(jobPostingService.getJobById(jobPostingDto.getId())).thenReturn(jobPostingDto);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + jobPostingDto.getId())
                        .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(Math.toIntExact(jobPostings.get(0).getId()))))
                .andExpect(jsonPath("$.description", Matchers.is(jobPostings.get(0).getDescription())))
                .andExpect(jsonPath("$.location", Matchers.is(jobPostings.get(0).getLocation())))
                .andExpect(jsonPath("$.keywords", Matchers.is(jobPostings.get(0).getKeywords())))
                .andExpect(jsonPath("$.salary", Matchers.is(jobPostings.get(0).getSalary())))
                .andReturn();
    }

    @Test
    void getJobByIdWithoutJwtShouldBeForbidden() throws Exception {

        JobPostingDto jobPostingDto = JobPostingDto.builder()
                .id(1L)
                .createdAt(Instant.now())
                .description("description")
                .location("Paris")
                .keywords("toto,yoyo")
                .salary("1111$")
                .build();

        when(jobPostingService.getJobById(jobPostingDto.getId())).thenReturn(jobPostingDto);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/" + jobPostingDto.getId())
                        .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }
}


