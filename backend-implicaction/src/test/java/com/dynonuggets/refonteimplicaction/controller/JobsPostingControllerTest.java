package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.JobPostingService;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import com.dynonuggets.refonteimplicaction.utils.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.GET_JOB_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.JOB_BASE_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobPostingController.class)
class JobsPostingControllerTest {

    private final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");


    @Autowired
    private MockMvc mvc;

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

        given(jobPostingService.findAllWithCriteria(DEFAULT_PAGEABLE, search, contractType)).willReturn(jobPostingPageMockResponse);

        actions = mvc.perform(get(JOB_BASE_URI).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(jobPostingPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(jobPostings.size()));

        for (int i = 0; i < jobPostings.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(jobPostings.get(i).getId()))))
                    .andExpect(jsonPath(contentPath + ".createdAt", is(jobPostings.get(i).getCreatedAt().toString())))
                    .andExpect(jsonPath(contentPath + ".description", is(jobPostings.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".location", is(jobPostings.get(i).getLocation())))
                    .andExpect(jsonPath(contentPath + ".keywords", is(jobPostings.get(i).getKeywords())))
                    .andExpect(jsonPath(contentPath + ".salary", is(jobPostings.get(i).getSalary())));
        }
        actions.andReturn();
        verify(jobPostingService, times(1)).findAllWithCriteria(any(), anyString(), anyString());
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        mvc.perform(get(JOB_BASE_URI)).andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
        verify(jobPostingService, times(0)).findAllWithCriteria(any(), anyString(), anyString());
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

        given(jobPostingService.getJobById(jobPostingDto.getId())).willReturn(jobPostingDto);

        mvc.perform(get(JOB_BASE_URI + GET_JOB_URI, jobPostingDto.getId()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(jobPostings.get(0).getId()))))
                .andExpect(jsonPath("$.description", is(jobPostings.get(0).getDescription())))
                .andExpect(jsonPath("$.location", is(jobPostings.get(0).getLocation())))
                .andExpect(jsonPath("$.keywords", is(jobPostings.get(0).getKeywords())))
                .andExpect(jsonPath("$.salary", is(jobPostings.get(0).getSalary())))
                .andReturn();
        verify(jobPostingService, times(1)).getJobById(anyLong());
    }

    @Test
    @WithMockUser
    void getJobByIdWhithUnexistingIdShouldThrowException() throws Exception {
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(Message.JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.getJobById(anyLong())).willThrow(exception);

        mvc.perform(get(JOB_BASE_URI + GET_JOB_URI, jobId)).andExpect(status().isNotFound());

        verify(jobPostingService, times(1)).getJobById(anyLong());
    }

    @Test
    void getJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        final long jobId = 123L;
        mvc.perform(get(JOB_BASE_URI + GET_JOB_URI, jobId).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
        verify(jobPostingService, times(0)).getJobById(anyLong());
    }
}