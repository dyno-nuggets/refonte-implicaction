package com.dynonuggets.refonteimplicaction.feature.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.feature.service.FeatureService;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.feature.model.enums.FeatureKey.EMAIL_NOTIFICATION;
import static com.dynonuggets.refonteimplicaction.feature.utils.FeatureUris.FEATURE_BASE_URI;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FeatureController.class)
class FeatureControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = FEATURE_BASE_URI;

    @MockBean
    FeatureService featureService;

    @Nested
    @DisplayName("# getAll")
    class GetAll {
        private void launchGetAllTestInSuccess() throws Exception {
            // given
            final List<FeatureDto> features = of(FeatureDto.builder().featureKey(EMAIL_NOTIFICATION).active(true).build());
            given(featureService.getAll()).willReturn(features);

            // when
            final ResultActions resultActions = mvc.perform(get(baseUri)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.size()", is(features.size())));
            verify(featureService, times(1)).getAll();
        }

        @Test
        @WithMockUser(roles = {"ADMIN"})
        @DisplayName("doit répondre OK quand l'utilisateur est ADMIN")
        void should_response_ok_when_user_is_ADMIN() throws Exception {
            launchGetAllTestInSuccess();
        }


        @Test
        @WithMockUser
        @DisplayName("doit répondre OK quand l'utilisateur est USER")
        void should_response_ok_when_user_is_USER() throws Exception {
            launchGetAllTestInSuccess();
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // given
            final List<FeatureDto> features = of(FeatureDto.builder().featureKey(EMAIL_NOTIFICATION).active(true).build());
            given(featureService.getAll()).willReturn(features);

            // when
            final ResultActions resultActions = mvc.perform(get(baseUri)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(featureService);
        }
    }
}
