package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.ApplicationRequest;
import com.dynonuggets.refonteimplicaction.dto.JobApplicationDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.ApplyStatusEnum;
import com.dynonuggets.refonteimplicaction.model.ContractTypeEnum;
import com.dynonuggets.refonteimplicaction.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.APPLY_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.Message.APPLY_ALREADY_EXISTS_FOR_JOB;
import static com.dynonuggets.refonteimplicaction.utils.Message.JOB_NOT_FOUND_MESSAGE;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
        ApplicationRequest request = new ApplicationRequest(123L, ApplyStatusEnum.PENDING);
        JobApplicationDto response = new JobApplicationDto(243L, 123L, "Mon super Job", "Google", "http://uri.com", ApplyStatusEnum.PENDING, ContractTypeEnum.CDI);
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
        ApplicationRequest request = new ApplicationRequest(123L, ApplyStatusEnum.PENDING);
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
        ApplicationRequest request = new ApplicationRequest(123L, ApplyStatusEnum.PENDING);
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
        ApplicationRequest request = new ApplicationRequest(123L, ApplyStatusEnum.PENDING);
        String json = gson.toJson(request);

        // when
        final ResultActions resultActions = mvc.perform(
                post(APPLY_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isForbidden());

        verify(applicationService, never()).createApplyIfNotExists(any());
    }
}
