package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.service.JobPostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobPostingController.class)
class JobsPostingControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    JobPostingService jobPostingService;

    List<JobPostingDto> jobPostings;
    StatusDto statusAvailable;
    StatusDto statusArchived;

    @BeforeEach
    void setUp() {
        statusAvailable = StatusDto.builder().id(1L).label("jobAvailable").type("job_posting").build();
        statusArchived = StatusDto.builder().id(2L).label("jobArchived").type("job_posting").build();
        jobPostings = Arrays.asList(
                JobPostingDto.builder().id(1L).createdAt(Instant.now()).description("description").location("Paris").keywords("toto,yoyo").salary("1111$").build(),
                JobPostingDto.builder().id(2L).createdAt(Instant.now()).description("description2").location("New York").keywords("toto2,yoyo2").salary("2222$").build());
    }

    @WithMockUser
    @Test
    void getJobPostingsListShouldListAllJobs() throws Exception {
        // given
        String search = "";
        String contractType = "";
        Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);

        given(jobPostingService.findAllWithCriteria(DEFAULT_PAGEABLE, search, contractType)).willReturn(jobPostingPageMockResponse);

        ResultActions actions = mvc.perform(get(JOBS_BASE_URI).contentType(APPLICATION_JSON))
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

        verify(jobPostingService, times(1)).findAllWithCriteria(any(), anyString(), anyString());
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        mvc.perform(get(JOBS_BASE_URI)).andDo(print())
                .andExpect(status().isForbidden());

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

        mvc.perform(get(JOBS_BASE_URI + GET_JOB_URI, jobPostingDto.getId()).contentType(APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(jobPostings.get(0).getId()))))
                .andExpect(jsonPath("$.description", is(jobPostings.get(0).getDescription())))
                .andExpect(jsonPath("$.location", is(jobPostings.get(0).getLocation())))
                .andExpect(jsonPath("$.keywords", is(jobPostings.get(0).getKeywords())))
                .andExpect(jsonPath("$.salary", is(jobPostings.get(0).getSalary())));

        verify(jobPostingService, times(1)).getJobById(anyLong());
    }

    @Test
    @WithMockUser
    void getJobByIdWhithUnexistingIdShouldThrowException() throws Exception {
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.getJobById(anyLong())).willThrow(exception);

        mvc.perform(get(JOBS_BASE_URI + GET_JOB_URI, jobId)).andExpect(status().isNotFound());

        verify(jobPostingService, times(1)).getJobById(anyLong());
    }

    @Test
    void getJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        final long jobId = 123L;
        mvc.perform(get(JOBS_BASE_URI + GET_JOB_URI, jobId).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(jobPostingService, times(0)).getJobById(anyLong());
    }

    @Test
    @WithMockUser
    void archiveJobShouldChangeStatus() throws Exception {
        JobPostingDto jobPostingDtoAvailable = JobPostingDto.builder()
                .id(1L)
                .createdAt(Instant.now())
                .description("description")
                .location("Paris")
                .keywords("toto,yoyo")
                .salary("1111$")
                .status(statusAvailable)
                .build();

        JobPostingDto jobPostingDtoArchived = JobPostingDto.builder()
                .id(1L)
                .createdAt(Instant.now())
                .description("description")
                .location("Paris")
                .keywords("toto,yoyo")
                .salary("1111$")
                .status(statusArchived)
                .build();

        given(jobPostingService.archiveOrUnarchiveJobPosting(jobPostingDtoAvailable.getId())).willReturn(jobPostingDtoArchived);

        mvc.perform(patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, jobPostingDtoAvailable.getId()).contentType(APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(jobPostingDtoAvailable.getId()))))
                .andExpect(jsonPath("$.description", is(jobPostingDtoAvailable.getDescription())))
                .andExpect(jsonPath("$.location", is(jobPostingDtoAvailable.getLocation())))
                .andExpect(jsonPath("$.keywords", is(jobPostingDtoAvailable.getKeywords())))
                .andExpect(jsonPath("$.salary", is(jobPostingDtoAvailable.getSalary())))
                .andExpect(jsonPath("$.status.label", is(jobPostingDtoArchived.getStatus().getLabel())));

        verify(jobPostingService, times(1)).archiveOrUnarchiveJobPosting(anyLong());
    }

    @Test
    @WithMockUser
    void archiveJobPostingWhithUnexistingIdShouldThrowException() throws Exception {
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.archiveOrUnarchiveJobPosting(anyLong())).willThrow(exception);

        mvc.perform(patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, jobId)).andExpect(status().isNotFound());

        verify(jobPostingService, times(1)).archiveOrUnarchiveJobPosting(anyLong());
    }

    @Test
    void archiveJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        final long jobId = 123L;
        mvc.perform(patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, jobId).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(jobPostingService, times(0)).archiveOrUnarchiveJobPosting(anyLong());
    }
}
