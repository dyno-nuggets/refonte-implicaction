package com.dynonuggets.refonteimplicaction.community.relation.controller;

import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationCreationRequest;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.error.RelationException;
import com.dynonuggets.refonteimplicaction.community.relation.service.RelationService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.community.relation.error.RelationErrorResult.RELATION_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationTestUtils.generateRandomRelationDto;
import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationTestUtils.resultActionsAssertionForSingleRelation;
import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationUris.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertErrorResult;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RelationController.class)
public class RelationControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = RELATION_BASE_URI;

    @MockBean
    RelationService relationService;

    @Nested
    @DisplayName("# createRelation")
    class CreateRelationTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la relation créée quand l'utilisateur est identifié et que token csrf est présent")
        void should_response_ok_with_created_relation_when_user_is_authenticated_and_csf_is_present() throws Exception {
            // given
            final String receiverName = "receiverName";
            final UserModel currentUser = UserModel.builder().username("currentUser").build();
            final RelationCreationRequest creationRequest = RelationCreationRequest.builder().sender(currentUser.getUsername()).receiver(receiverName).build();
            final RelationsDto relationsDto = generateRandomRelationDto(false, currentUser.getUsername(), receiverName);
            given(relationService.requestRelation(anyString(), anyString())).willReturn(relationsDto);

            // when
            final ResultActions resultActions = mvc.perform(post(RELATION_BASE_URI)
                    .content(toJson(creationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActionsAssertionForSingleRelation(resultActions, relationsDto);
            verify(relationService, times(1)).requestRelation(anyString(), anyString());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié et que token csrf est manquant")
        void should_response_forbidden_when_user_is_authenticated_and_csrf_is_missing() throws Exception {
            // given
            final String receiverName = "receiverName";
            final UserModel currentUser = UserModel.builder().username("currentUser").build();
            final RelationCreationRequest creationRequest = RelationCreationRequest.builder().sender(currentUser.getUsername()).receiver(receiverName).build();

            // when
            final ResultActions resultActions = mvc.perform(post(RELATION_BASE_URI)
                    .content(toJson(creationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // given
            final String receiverName = "receiverName";
            final UserModel currentUser = UserModel.builder().username("currentUser").build();
            final RelationCreationRequest creationRequest = RelationCreationRequest.builder().sender(currentUser.getUsername()).receiver(receiverName).build();

            // when
            final ResultActions resultActions = mvc.perform(post(RELATION_BASE_URI)
                    .content(toJson(creationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# removeRelation")
    class RemoveRelationTest {

        @Test
        @WithMockUser
        @DisplayName("doit répondre NO_CONTENT quand l'utilisateur est identifié et que le token csrf est présent")
        void should_response_no_content_when_removeRelation_and_user_is_authenticated_and_csrf_is_present() throws Exception {
            // given
            willDoNothing().given(relationService).removeRelation(anyLong());

            // when
            final ResultActions resultActions = mvc.perform(delete(getFullPath(REMOVE_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isNoContent());
            verify(relationService, times(1)).removeRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_forbidden_when_removeRelation_and_user_is_authenticated_but_csrf_is_missing() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(delete(getFullPath(REMOVE_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_notfound_when_removeRelation_and_user_is_authenticated_and_csrf_is_present_but_relation_not_exists() throws Exception {
            // given
            willThrow(new RelationException(RELATION_NOT_FOUND)).given(relationService).removeRelation(anyLong());

            // when
            final ResultActions resultActions = mvc.perform(delete(getFullPath(REMOVE_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            assertErrorResult(resultActions, RELATION_NOT_FOUND);
            verify(relationService, times(1)).removeRelation(anyLong());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_removeRelation_and_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(delete(getFullPath(REMOVE_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# confirmRelation")
    class ConfirmRelationTest {

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la relation modifiée quand l'utilisateur est identifié et que le token csrf est présent")
        void should_response_ok_with_updated_relation_when_user_is_authenticated_and_csrf_is_present() throws Exception {
            // given
            final RelationsDto relationsDto = generateRandomRelationDto(true, "randomUser", "currentUser");
            given(relationService.confirmRelation(anyLong())).willReturn(relationsDto);

            //when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CONFIRM_RELATION), relationsDto.getId())
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActionsAssertionForSingleRelation(resultActions, relationsDto);
            verify(relationService, times(1)).confirmRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre NOT_FOUND l'utilisateur est identifié et que le token csrf est présent mais que la relation n'existe pas")
        void should_response_notfound_when_user_is_authenticated_and_csrf_is_present_but_relation_not_exists() throws Exception {
            // given
            final RelationsDto relationsDto = generateRandomRelationDto(true, "randomUser", "currentUser");
            given(relationService.confirmRelation(anyLong())).willThrow(new RelationException(RELATION_NOT_FOUND));

            //when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CONFIRM_RELATION), relationsDto.getId())
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            assertErrorResult(resultActions, RELATION_NOT_FOUND);
            verify(relationService, times(1)).confirmRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais qu'il n'est pas autorisé à modifier la relation")
        void should_response_forbidden_when_user_is_not_granted_to_update_relation() throws Exception {
            // given
            given(relationService.confirmRelation(anyLong())).willThrow(new CoreException(OPERATION_NOT_PERMITTED));

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CONFIRM_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            assertErrorResult(resultActions, OPERATION_NOT_PERMITTED);
            verify(relationService, times(1)).confirmRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_forbidden_when_user_is_authenticated_but_csrf_is_missing() throws Exception {
            //when - then
            mvc.perform(post(getFullPath(CONFIRM_RELATION), 12L)).andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            //when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CONFIRM_RELATION), 12L)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }
}
