package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationRequest;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum.*;
import static com.dynonuggets.refonteimplicaction.model.ContractTypeEnum.*;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.APPLY_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.Message.APPLY_ALREADY_EXISTS_FOR_JOB;
import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = JobApplicationController.class)
class JobApplicationControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    JobApplicationService applicationService;

    @Test
    @WithMockUser
    void should_create_apply() throws Exception {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, PENDING);
        JobApplicationDto response = new JobApplicationDto(243L, 123L, "Mon super Job", "Google", "http://uri.com", PENDING, CDI);
        given(applicationService.createApplyIfNotExists(any())).willReturn(response);
        String json = gson.toJson(request);

        // when
        final ResultActions resultActions = mvc.perform(
                post(APPLY_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(response.getId().intValue())))
                .andExpect(jsonPath("$.jobId", is(response.getJobId().intValue())))
                .andExpect(jsonPath("$.jobTitle", is(response.getJobTitle())))
                .andExpect(jsonPath("$.companyName", is(response.getCompanyName())))
                .andExpect(jsonPath("$.companyImageUri", is(response.getCompanyImageUri())))
                .andExpect(jsonPath("$.status", is(response.getStatus().name())));

        verify(applicationService, times(1)).createApplyIfNotExists(any());
    }

    @Test
    @WithMockUser
    void should_return_notfound_when_creating_apply_and_job_notfound() throws Exception {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, PENDING);
        String json = gson.toJson(request);
        final NotFoundException exception = new NotFoundException(String.format(JOB_NOT_FOUND_MESSAGE, request.getJobId()));
        given(applicationService.createApplyIfNotExists(any())).willThrow(exception);

        // when
        final ResultActions resultActions = mvc.perform(
                post(APPLY_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage", is(exception.getMessage())));

        verify(applicationService, times(1)).createApplyIfNotExists(any());
    }

    @Test
    @WithMockUser
    void should_return_bad_request_when_creating_apply_with_already_applied_job() throws Exception {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, PENDING);
        String json = gson.toJson(request);
        final IllegalArgumentException exception = new IllegalArgumentException(String.format(APPLY_ALREADY_EXISTS_FOR_JOB, request.getJobId()));
        given(applicationService.createApplyIfNotExists(any())).willThrow(exception);

        // when
        final ResultActions resultActions = mvc.perform(
                post(APPLY_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", is(exception.getMessage())));

        verify(applicationService, times(1)).createApplyIfNotExists(any());
    }

    @Test
    void should_return_forbidden_when_creating_apply_and_no_auth() throws Exception {
        // given
        JobApplicationRequest request = new JobApplicationRequest(123L, PENDING);
        String json = gson.toJson(request);

        // when
        final ResultActions resultActions = mvc.perform(
                post(APPLY_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isForbidden());

        verify(applicationService, never()).createApplyIfNotExists(any());
    }

    @Test
    @WithMockUser
    void should_list_all_users_application() throws Exception {
        // given
        List<JobApplicationDto> expecteds = asList(
                new JobApplicationDto(1L, 12L, "super job", "google", "http://url.com", PENDING, CDD),
                new JobApplicationDto(2L, 13L, "super job 2", "microsof", "http://url2.com", CHASED, CDD),
                new JobApplicationDto(3L, 14L, "super job 3", "amazon", "http://url3.com", INTERVIEW, INTERIM)
        );
        int expectedSize = expecteds.size();
        given(applicationService.getAllAppliesForCurrentUser()).willReturn(expecteds);

        // when
        final ResultActions resultActions = mvc.perform(get(APPLY_BASE_URI));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expectedSize)));

        for (int i = 0; i < expectedSize; i++) {
            String contentPath = String.format("$[%d].", i);
            resultActions.andExpect(jsonPath(contentPath + "id", is(expecteds.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + "jobId", is(expecteds.get(i).getJobId().intValue())))
                    .andExpect(jsonPath(contentPath + "companyName", is(expecteds.get(i).getCompanyName())))
                    .andExpect(jsonPath(contentPath + "companyImageUri", is(expecteds.get(i).getCompanyImageUri())))
                    .andExpect(jsonPath(contentPath + "status", is(expecteds.get(i).getStatus().name())))
                    .andExpect(jsonPath(contentPath + "contractType", is(expecteds.get(i).getContractType().name())));
        }

        verify(applicationService, times(1)).getAllAppliesForCurrentUser();
    }

    @Test
    void should_response_forbidden_when_listing_and_no_auth() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(APPLY_BASE_URI));

        // then
        resultActions.andExpect(status().isForbidden());
    }
}
