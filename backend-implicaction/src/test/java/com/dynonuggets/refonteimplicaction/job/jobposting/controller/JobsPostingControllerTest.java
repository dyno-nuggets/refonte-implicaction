package com.dynonuggets.refonteimplicaction.job.jobposting.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.job.jobposting.adapter.JobPostingAdapter;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.service.JobPostingService;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.job.jobposting.dto.enums.ContractTypeEnum.CDD;
import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.*;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = JobPostingController.class)
class JobsPostingControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = JOBS_BASE_URI;

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
        final Page<JobPostingDto> jobPostingPageMockResponse = new PageImpl<>(jobPostings);
        given(jobPostingService.getAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean(), any())).willReturn(jobPostingPageMockResponse);

        // when
        final ResultActions resultActions = mvc.perform(get(baseUri)
                .param("contractType", CDD.name())
                .param("archive", "true")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(jobPostingPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(jobPostings.size()));

        for (int i = 0; i < jobPostings.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions
                    .andExpect(jsonPath(contentPath + ".id", is(jobPostings.get(i).getId().intValue())))
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
        // when
        final ResultActions resultActions = mvc.perform(get(baseUri)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verify(jobPostingService, times(0)).getAllWithCriteria(any(), anyString(), any(), any(), anyBoolean(), anyBoolean(), any());
    }

    @Test
    @WithMockUser
    void getJobByIdShouldReturnOneJob() throws Exception {
        // given
        final Long jobPostingId = 1L;
        final JobPostingDto jobPostingDto = JobPostingDto.builder()
                .id(jobPostingId)
                .createdAt(Instant.now())
                .description("description")
                .location("Paris")
                .keywords("toto,yoyo")
                .salary("1111$")
                .build();
        given(jobPostingService.getJobById(jobPostingId)).willReturn(jobPostingDto);

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_JOB_URI), jobPostingId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(jobPostings.get(0).getId()))))
                .andExpect(jsonPath("$.description", is(jobPostings.get(0).getDescription())))
                .andExpect(jsonPath("$.location", is(jobPostings.get(0).getLocation())))
                .andExpect(jsonPath("$.keywords", is(jobPostings.get(0).getKeywords())))
                .andExpect(jsonPath("$.salary", is(jobPostings.get(0).getSalary())));
        verify(jobPostingService, times(1)).getJobById(jobPostingId);
    }

    @Test
    @WithMockUser
    void getJobByIdWithNonexistentIdShouldThrowException() throws Exception {
        // given
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.getJobById(jobId)).willThrow(exception);

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_JOB_URI), jobId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
        verify(jobPostingService, times(1)).getJobById(jobId);
    }

    @Test
    void getJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_JOB_URI), 123L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verifyNoInteractions(jobPostingService);
    }

    @Test
    @WithMockUser
    void archiveJobShouldChangeStatus() throws Exception {
        // given
        final JobPostingDto givenDto = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();

        final JobPostingDto expectedDto = JobPostingDto.builder()
                .id(1L)
                .archive(true)
                .build();

        given(jobPostingService.toggleArchiveJobPosting(expectedDto.getId())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                patch(getFullPath(ARCHIVE_JOB_URI), givenDto.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .with(csrf()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((givenDto.getId().intValue()))))
                .andExpect(jsonPath("$.archive", is(!givenDto.isArchive())));
        verify(jobPostingService, times(1)).toggleArchiveJobPosting(givenDto.getId());
    }

    @Test
    @WithMockUser
    void archiveJobShouldChangeListStatus() throws Exception {
        // given
        final JobPostingDto job = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();

        final List<Long> givenJobIds = Collections.singletonList(job.getId());

        final List<JobPostingDto> expectedDtos = Collections.singletonList(
                JobPostingDto.builder()
                        .id(1L)
                        .archive(true)
                        .build()
        );

        given(jobPostingService.toggleArchiveAll(givenJobIds)).willReturn(expectedDtos);

        // when
        final ResultActions resultActions = mvc.perform(patch(getFullPath(ARCHIVE_JOBS_URI))
                .content(toJson(givenJobIds))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
        for (int i = 0; i < givenJobIds.size(); i++) {
            final String contentPath = String.format("$[%d]", i);
            resultActions
                    .andExpect(jsonPath(contentPath + ".id", is(job.getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".archive", is(!job.isArchive())));
            verify(jobPostingService, times(1)).toggleArchiveAll(givenJobIds);
        }
    }

    @Test
    @WithMockUser
    void archiveJobPostingWithNonexistentIdShouldThrowException() throws Exception {
        // given
        final long jobId = 123L;
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, jobId));
        given(jobPostingService.toggleArchiveJobPosting(jobId)).willThrow(exception);

        // when
        final ResultActions resultActions = mvc.perform(patch(getFullPath(ARCHIVE_JOB_URI), jobId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

        // then
        resultActions.andExpect(status().isNotFound());
        verify(jobPostingService, times(1)).toggleArchiveJobPosting(jobId);
    }

    @Test
    void archiveJobByIdWithoutJwtShouldBeForbidden() throws Exception {
        // given
        final long jobId = 123L;

        // when
        final ResultActions resultActions = mvc.perform(patch(getFullPath(ARCHIVE_JOB_URI), jobId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verifyNoInteractions(jobPostingService);
    }

    @Test
    void archiveJobListWithoutJwtShouldBeForbidden() throws Exception {
        // given
        final JobPostingDto job = JobPostingDto.builder()
                .id(1L)
                .archive(false)
                .build();
        final List<Long> givenJobIds = Collections.singletonList(job.getId());

        // when
        final ResultActions resultActions = mvc.perform(patch(getFullPath(ARCHIVE_JOBS_URI))
                .content(toJson(givenJobIds))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verifyNoInteractions(jobPostingService);
    }

    @Test
    @WithMockUser
    void should_get_all_pending_jobs_when_authenticated() throws Exception {
        //given
        final List<JobPostingDto> jobPostingDtos = of(
                JobPostingDto.builder().id(1L).valid(false).build(),
                JobPostingDto.builder().id(2L).valid(false).build(),
                JobPostingDto.builder().id(3L).valid(false).build()
        );
        final Page<JobPostingDto> jobPageMockResponse = new PageImpl<>(jobPostingDtos);
        given(jobPostingService.getAllPendingJobs(any(Pageable.class))).willReturn(jobPageMockResponse);

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PENDING_JOB_URI))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
        for (int i = 0; i < jobPostingDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(jobPostingDtos.get(i).getId()))));
            resultActions.andExpect(jsonPath(contentPath + ".valid", is(jobPostingDtos.get(i).isValid())));
        }
        verify(jobPostingService, times(1)).getAllPendingJobs(any(Pageable.class));
    }

    @Test
    void should_response_forbidden_when_pending_jobs_and_not_authenticated() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PENDING_JOB_URI))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
        verifyNoInteractions(jobPostingService);
    }

}
