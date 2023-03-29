package com.dynonuggets.refonteimplicaction.job.jobposting.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.job.jobposting.dto.JobPostingDto;
import com.dynonuggets.refonteimplicaction.job.jobposting.service.JobPostingService;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.GET_LATEST_JOBS_URI;
import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.PUBLIC_JOBS_BASE_URI;
import static java.lang.String.valueOf;
import static java.util.List.of;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PublicJobPostingController.class)
public class PublicJobPostingControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = PUBLIC_JOBS_BASE_URI;

    @MockBean
    JobPostingService jobPostingService;

    @Nested
    @DisplayName("# getLatestJobs")
    class GetLatestJobsTests {
        @Test
        @DisplayName("doit répondre OK avec la liste des derniers jobs quand l'utilisateur n'est pas identifié et qu'aucun paramètre n'est fourni")
        void should_response_ok_with_a_list_of_jobs_when_not_authenticated_and_no_row_count_given() throws Exception {
            // given
            given(jobPostingService.getLatestJobs(anyInt())).willReturn(of(JobPostingDto.builder().build()));

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_LATEST_JOBS_URI))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            verify(jobPostingService, times(1)).getLatestJobs(anyInt());
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON));
        }

        @Test
        @DisplayName("doit répondre OK avec la liste des derniers jobs quand l'utilisateur n'est pas identifié et qu'un nom de ligne est fourni")
        void should_response_ok_with_a_list_of_jobs_when_row_count_is_given_with_no_authentication() throws Exception {
            // given
            final int jobCount = 2;
            given(jobPostingService.getLatestJobs(jobCount)).willReturn(of(JobPostingDto.builder().build()));

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_LATEST_JOBS_URI))
                    .param("rows", valueOf(jobCount))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            );

            // then
            verify(jobPostingService, times(1)).getLatestJobs(jobCount);
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON));
        }
    }
}
