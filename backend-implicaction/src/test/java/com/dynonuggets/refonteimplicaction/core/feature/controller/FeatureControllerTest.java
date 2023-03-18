package com.dynonuggets.refonteimplicaction.core.feature.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.feature.dto.FeatureDto;
import com.dynonuggets.refonteimplicaction.core.feature.service.FeatureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey.EMAIL_NOTIFICATION;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FeatureController.class)
class FeatureControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    FeatureService featureService;

    @Nested
    @DisplayName("# getAll")
    class GetAll {
        @Test
        @DisplayName("doit répondre OK avec la liste de toutes les features quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_admin() throws Exception {
            // given
            final List<FeatureDto> features = of(FeatureDto.builder().featureKey(EMAIL_NOTIFICATION).active(true).build());
            given(featureService.getAll()).willReturn(features);

            // when
            final ResultActions resultActions = mvc.perform(get("/api/features"));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.size()", is(features.size())));
            verify(featureService, times(1)).getAll();
        }
    }
}
