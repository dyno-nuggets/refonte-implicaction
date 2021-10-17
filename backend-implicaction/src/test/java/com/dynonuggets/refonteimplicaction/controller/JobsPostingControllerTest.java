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

    @Autowired
    protected MockMvc mvc;
    List<JobPostingDto> jobPostings;
    @InjectMocks
    private JobPostingController jobPostingController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private JobPostingService jobPostingService;

    @BeforeEach
    protected void setUp() {
        jobPostings = Arrays.asList(
                JobPostingDto.builder().id(1L).createdAt(Instant.now()).description("description").location("Paris").keywords("toto,yoyo").salary("1111$").build(),
                JobPostingDto.builder().id(2L).createdAt(Instant.now()).description("description2").location("New York").keywords("toto2,yoyo2").salary("2222$").build());
    }

    @WithMockUser
    @Test
    void getJobPostingsListShouldListAllJobs() throws Exception {

        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";
        String search = "";
        String contractType = "";

        Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);
        Pageable pageable = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));

        when(jobPostingService.findAllWithCriteria(pageable, search, contractType)).thenReturn(jobPostingPageMockResponse);
        mvc.perform(MockMvcRequestBuilders.get("/api/job-postings")
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id", Matchers.is(Math.toIntExact(jobPostings.get(0).getId()))))
                .andExpect(jsonPath("$.content[0].createdAt", Matchers.is(jobPostings.get(0).getCreatedAt().toString())))
                .andExpect(jsonPath("$.content[0].description", Matchers.is(jobPostings.get(0).getDescription())))
                .andExpect(jsonPath("$.content[0].location", Matchers.is(jobPostings.get(0).getLocation())))
                .andExpect(jsonPath("$.content[0].keywords", Matchers.is(jobPostings.get(0).getKeywords())))
                .andExpect(jsonPath("$.content[0].salary", Matchers.is(jobPostings.get(0).getSalary())))
                .andExpect(jsonPath("$.content[1].id", Matchers.is(Math.toIntExact(jobPostings.get(1).getId()))))
                .andExpect(jsonPath("$.content[1].createdAt", Matchers.is(jobPostings.get(1).getCreatedAt().toString())))
                .andExpect(jsonPath("$.content[1].description", Matchers.is(jobPostings.get(1).getDescription())))
                .andExpect(jsonPath("$.content[1].location", Matchers.is(jobPostings.get(1).getLocation())))
                .andExpect(jsonPath("$.content[1].keywords", Matchers.is(jobPostings.get(1).getKeywords())))
                .andExpect(jsonPath("$.content[1].salary", Matchers.is(jobPostings.get(1).getSalary())))
                .andReturn();
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";
        String search = "";
        String contractType = "";

        Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);
        Pageable pageable = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));

        when(jobPostingService.findAllWithCriteria(pageable, search, contractType)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mvc.perform(MockMvcRequestBuilders.get("/api/job-postings")
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
        mvc.perform(MockMvcRequestBuilders.get("/api/job-postings/" + jobPostingDto.getId())
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
        mvc.perform(MockMvcRequestBuilders.get("/api/job-postings/" + jobPostingDto.getId())
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }
}


