package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.dto.JobPostingDto;
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
import java.util.Collections;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.CDD;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobPostingController.class)
class JobsPostingControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    JobPostingService jobPostingService;

    @MockBean
    JobPostingAdapter jobPostingAdapter;

    List<JobPostingDto> jobPostings;

    @BeforeEach
    void setUp() {
        jobPostings = Arrays.asList(
                JobPostingDto.builder().id(1L).createdAt(Instant.now()).description("description").location("Paris").keywords("toto,yoyo").contractType(CDD).salary("1111$").build(),
                JobPostingDto.builder().id(2L).createdAt(Instant.now()).description("description2").location("New York").keywords("toto2,yoyo2").contractType(CDD).salary("2222$").build());
    }

    @Test
    @WithMockUser
    void getJobPostingsListShouldListAllJobs() throws Exception {
        // given
        Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);

        given(jobPostingService.getAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean(), any())).willReturn(jobPostingPageMockResponse);

        // when
        ResultActions resultActions = mvc.perform(
                get(JOBS_BASE_URI).param("contractType", CDD.name()).param("archive", "true")

        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(jobPostingPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(jobPostings.size()));

        for (int i = 0; i < jobPostings.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(jobPostings.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".createdAt", is(jobPostings.get(i).getCreatedAt().toString())))
                    .andExpect(jsonPath(contentPath + ".description", is(jobPostings.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".location", is(jobPostings.get(i).getLocation())))
                    .andExpect(jsonPath(contentPath + ".keywords", is(jobPostings.get(i).getKeywords())))
                    .andExpect(jsonPath(contentPath + ".salary", is(jobPostings.get(i).getSalary())));
        }

        verify(jobPostingService, times(1)).getAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean(), any());
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        mvc.perform(get(JOBS_BASE_URI)).andDo(print())
                .andExpect(status().isForbidden());

        verify(jobPostingService, times(0)).getAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean(), any());
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
        // given
        JobPostingDto givenDto = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();

        JobPostingDto expectedDto = JobPostingDto.builder()
                .id(1L)
                .archive(true)
                .build();

        given(jobPostingService.toggleArchiveJobPosting(anyLong())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, givenDto.getId()).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((givenDto.getId().intValue()))))
                .andExpect(jsonPath("$.archive", is(!givenDto.isArchive())));
        verify(jobPostingService, times(1)).toggleArchiveJobPosting(anyLong());
    }

    @Test
    @WithMockUser
    void archiveJobShouldChangeListStatus() throws Exception {
        // given
        JobPostingDto job = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();

        List<Long> givenDto = Collections.singletonList(job.getId());

        List<JobPostingDto> expectedDto = Collections.singletonList(
                JobPostingDto.builder()
                        .id(1L)
                        .archive(true)
                        .build()
        );

        String json = gson.toJson(givenDto);

        given(jobPostingService.toggleArchiveAll(anyList())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                patch(JOBS_BASE_URI + ARCHIVE_JOBS_URI).contentType(APPLICATION_JSON).content(json).with(csrf())
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < givenDto.size(); i++) {
            final String contentPath = String.format("$[%d]", i);
            resultActions
                    .andExpect(jsonPath(contentPath + ".id", is(job.getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".archive", is(!job.isArchive())));
            verify(jobPostingService, times(1)).toggleArchiveAll(givenDto);
        }
    }

    @Test
    @WithMockUser
    void archiveJobPostingWhithUnexistingIdShouldThrowException() throws Exception {
        // given
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.toggleArchiveJobPosting(anyLong())).willThrow(exception);

        // when
        ResultActions resultActions = mvc.perform(
                patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, jobId).with(csrf())
        );

        // then
        resultActions.andExpect(status().isNotFound());
        verify(jobPostingService, times(1)).toggleArchiveJobPosting(anyLong());
    }

    @Test
    void archiveJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        // given
        final long jobId = 123L;

        // when
        ResultActions resultActions = mvc.perform(patch(JOBS_BASE_URI + ARCHIVE_JOB_URI, jobId).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isForbidden());
        verify(jobPostingService, never()).toggleArchiveJobPosting(anyLong());
    }

    @Test
    void archiveJobListWithoutJwtShouldBeForbidden() throws Exception {
        // given
        JobPostingDto job = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();

        List<Long> givenDto = Collections.singletonList(job.getId());
        String json = gson.toJson(givenDto);

        // when
        ResultActions resultActions = mvc.perform(patch(JOBS_BASE_URI + ARCHIVE_JOBS_URI).contentType(APPLICATION_JSON).content(json));

        // then
        resultActions.andDo(print())
                .andExpect(status().isForbidden());
        verify(jobPostingService, never()).toggleArchiveAll(Collections.singletonList(anyLong()));
    }

    @Test
    @WithMockUser
    void should_get_all_pending_jobs_when_authenticated() throws Exception {
        //given
        List<JobPostingDto> jobPostingDtos = Arrays.asList(
                JobPostingDto.builder().id(1L).valid(false).build(),
                JobPostingDto.builder().id(2L).valid(false).build(),
                JobPostingDto.builder().id(3L).valid(false).build()
        );
        Page<JobPostingDto> jobPageMockResponse = new PageImpl<>(jobPostingDtos);
        given(jobPostingService.getAllPendingJobs(any())).willReturn(jobPageMockResponse);

        // when
        ResultActions resultActions = mvc.perform(
                get(JOBS_BASE_URI + GET_PENDING_JOB_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < jobPostingDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(jobPostingDtos.get(i).getId()))));
            resultActions.andExpect(jsonPath(contentPath + ".valid", is(jobPostingDtos.get(i).isValid())));
        }

        verify(jobPostingService, times(1)).getAllPendingJobs(any());
    }

    @Test
    void should_response_forbidden_when_pending_jobs_and_not_authenticated() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(get(JOBS_BASE_URI + GET_PENDING_JOB_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(jobPostingService, never()).getAllPendingJobs(any());
    }

}
