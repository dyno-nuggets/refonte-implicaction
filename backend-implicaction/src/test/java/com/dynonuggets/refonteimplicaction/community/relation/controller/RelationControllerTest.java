package com.dynonuggets.refonteimplicaction.community.relation.controller;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.error.RelationException;
import com.dynonuggets.refonteimplicaction.community.relation.service.RelationService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.relation.error.RelationErrorResult.RELATION_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationTestUtils.*;
import static com.dynonuggets.refonteimplicaction.community.relation.utils.RelationUris.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertErrorResult;
import static java.util.List.of;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RelationController.class)
public class RelationControllerTest extends ControllerIntegrationTestBase {
    @MockBean
    RelationService relationService;
    @MockBean
    AuthService authService;

    @Nested
    @DisplayName("# getAllCommunity")
    class GetAllCommunityTest {

        static final String TEST_URI = RELATION_BASE_URI + GET_ALL_COMMUNITY;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste de toutes les relations à l'utilisateur quand l'utilisateur est identifié")
        void should_response_ok_with_the_list_of_all_relations_with_authenticated_user_when_user_is_autneticated() throws Exception {
            final String currentUsername = "currentUser";
            final List<RelationsDto> relationsDtos = of(
                    generateRandomRelationDto(true, currentUsername, randomAlphabetic(20)),
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername),
                    generateRandomRelationDto(true, null, randomAlphabetic(20)));
            final PageImpl<RelationsDto> expected = new PageImpl<>(relationsDtos);
            given(relationService.getAllCommunity(any(Pageable.class))).willReturn(expected);

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI));

            // then
            resultActionsAssertionsForRelations(resultActions, expected);
            verify(relationService, times(1)).getAllCommunity(any(Pageable.class));
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# getAllRelationsByUsername")
    class GetAllRelationsByUsername {

        static final String TEST_URI = RELATION_BASE_URI + GET_ALL_RELATIONS_URI;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste des relations de l'utilisateur quand l'utilisateur est identifié")
        void should_response_ok_with_paginated_list_of_relation_when_user_is_authenticated() throws Exception {
            // given
            final String currentUsername = "currentUser";
            final List<RelationsDto> relationsDtos = of(
                    generateRandomRelationDto(true, currentUsername, randomAlphabetic(20)),
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername),
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername));
            final PageImpl<RelationsDto> expected = new PageImpl<>(relationsDtos);
            given(relationService.getAllRelationsByUsername(anyString(), any(Pageable.class))).willReturn(expected);

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, currentUsername));

            // then
            resultActionsAssertionsForRelations(resultActions, expected);
            verify(relationService, times(1)).getAllRelationsByUsername(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, "username"));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# getSentFriendRequest")
    class GetSentFriendRequestTest {

        static final String TEST_URI = RELATION_BASE_URI + GET_ALL_RELATIONS_REQUESTS_SENT_URI;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste des relations de l'utilisateur quand l'utilisateur est identifié")
        void should_response_ok_with_paginated_list_of_relation_when_user_is_authenticated() throws Exception {
            // given
            final String currentUsername = "currentUser";
            final List<RelationsDto> relationsDtos = of(
                    generateRandomRelationDto(true, currentUsername, randomAlphabetic(20)),
                    generateRandomRelationDto(true, currentUsername, randomAlphabetic(20)),
                    generateRandomRelationDto(true, currentUsername, randomAlphabetic(20)));
            final PageImpl<RelationsDto> expected = new PageImpl<>(relationsDtos);
            given(relationService.getSentFriendRequest(anyString(), any(Pageable.class))).willReturn(expected);

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, currentUsername).contentType(APPLICATION_JSON));

            // then
            resultActionsAssertionsForRelations(resultActions, expected);
            verify(relationService, times(1)).getSentFriendRequest(anyString(), any(Pageable.class));
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN avec un ErrorResult correspondant quand l'utilisateur courant n'est pas autorisé à accéder aux demandes de relations de l'utilisateur")
        void should_response_forbidden_with_ErrorResult() throws Exception {
            // given
            given(relationService.getSentFriendRequest(anyString(), any(Pageable.class))).willThrow(new CoreException(OPERATION_NOT_PERMITTED));

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, "requestUsername"));

            // then
            assertErrorResult(resultActions, OPERATION_NOT_PERMITTED);
            verify(relationService, times(1)).getSentFriendRequest(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, "noMatter"));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# getReceivedFriendRequest")
    class GetReceivedFriendRequestTest {

        static final String TEST_URI = RELATION_BASE_URI + GET_ALL_RELATIONS_REQUESTS_RECEIVED_URI;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste des relations de l'utilisateur quand l'utilisateur est identifié")
        void should_response_ok_with_paginated_list_of_relation_when_user_is_authenticated() throws Exception {
            // given
            final String currentUsername = "currentUser";
            final List<RelationsDto> relationsDtos = of(
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername),
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername),
                    generateRandomRelationDto(true, randomAlphabetic(20), currentUsername));
            final PageImpl<RelationsDto> expected = new PageImpl<>(relationsDtos);
            given(relationService.getReceivedFriendRequest(anyString(), any(Pageable.class))).willReturn(expected);

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, currentUsername).contentType(APPLICATION_JSON));

            // then
            resultActionsAssertionsForRelations(resultActions, expected);
            verify(relationService, times(1)).getReceivedFriendRequest(anyString(), any(Pageable.class));
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN avec un ErrorResult correspondant")
        void should_response_forbidden_with_ErrorResult() throws Exception {
            // given
            given(relationService.getReceivedFriendRequest(anyString(), any(Pageable.class))).willThrow(new CoreException(OPERATION_NOT_PERMITTED));

            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, "requestUsername"));

            // then
            assertErrorResult(resultActions, OPERATION_NOT_PERMITTED);
            verify(relationService, times(1)).getReceivedFriendRequest(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(TEST_URI, "noMatter"));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# requestRelation")
    class RequestRelationTest {

        static final String TEST_URI = RELATION_BASE_URI + REQUEST_RELATION;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la relation créée quand l'utilisateur est identifié et que token csrf est présent")
        void should_response_ok_with_created_relation_when_user_is_authenticated_and_csf_is_present() throws Exception {
            // given
            final String receiverName = "receiverName";
            final UserModel currentUser = UserModel.builder().username("currentUser").build();
            given(authService.getCurrentUser()).willReturn(currentUser);
            final RelationsDto relationsDto = generateRandomRelationDto(false, currentUser.getUsername(), receiverName);
            given(relationService.requestRelation(anyString(), anyString())).willReturn(relationsDto);

            // when
            final ResultActions resultActions = mvc.perform(post(TEST_URI, receiverName).with(csrf()));

            // then
            resultActionsAssertionForSingleRelation(resultActions, relationsDto);
            verify(authService, times(1)).getCurrentUser();
            verify(relationService, times(1)).requestRelation(anyString(), anyString());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié et que token csrf est manquant")
        void should_response_forbidden_when_user_is_authenticated_and_csrf_is_missing() throws Exception {
            // when - then
            mvc.perform(post(TEST_URI, "receiverName")).andExpect(status().isForbidden());
            verifyNoInteractions(authService);
            verifyNoInteractions(relationService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when - then
            mvc.perform(post(TEST_URI, "receiverName")).andExpect(status().isForbidden());
            verifyNoInteractions(authService);
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# removeRelation")
    class RemoveRelationTest {
        static final String TEST_URI = RELATION_BASE_URI + REMOVE_RELATION;

        @Test
        @WithMockUser
        @DisplayName("doit répondre NO_CONTENT quand l'utilisateur est identifié et que le token csrf est présent")
        void should_response_no_content_when_removeRelation_and_user_is_authenticated_and_csrf_is_present() throws Exception {
            // given
            willDoNothing().given(relationService).removeRelation(anyLong());

            // when
            mvc.perform(delete(TEST_URI, 12L).with(csrf())).andExpect(status().isNoContent());
            verify(relationService, times(1)).removeRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_forbidden_when_removeRelation_and_user_is_authenticated_but_csrf_is_missing() throws Exception {
            // when
            mvc.perform(delete(TEST_URI, 12L)).andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_notfound_when_removeRelation_and_user_is_authenticated_and_csrf_is_present_but_relation_not_exists() throws Exception {
            // given
            willThrow(new RelationException(RELATION_NOT_FOUND)).given(relationService).removeRelation(anyLong());

            // when
            final ResultActions resultActions = mvc.perform(delete(TEST_URI, 12L).with(csrf()));
            assertErrorResult(resultActions, RELATION_NOT_FOUND);
            verify(relationService, times(1)).removeRelation(anyLong());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_removeRelation_and_user_is_not_authenticated() throws Exception {
            // when
            mvc.perform(delete(TEST_URI, 12L)).andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }

    @Nested
    @DisplayName("# confirmRelation")
    class ConfirmRelationTest {
        static final String TEST_URI = RELATION_BASE_URI + CONFIRM_RELATION;

        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la relation modifiée quand l'utilisateur est identifié et que le token csrf est présent")
        void should_response_ok_with_updated_relation_when_user_is_authenticated_and_csrf_is_present() throws Exception {
            // given
            final RelationsDto relationsDto = generateRandomRelationDto(true, "randomUser", "currentUser");
            given(relationService.confirmRelation(anyLong())).willReturn(relationsDto);

            //when
            final ResultActions resultActions = mvc.perform(post(TEST_URI, relationsDto.getId()).with(csrf()));

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
            final ResultActions resultActions = mvc.perform(post(TEST_URI, relationsDto.getId()).with(csrf()));

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
            final ResultActions resultActions = mvc.perform(post(TEST_URI, 12L).with(csrf()));

            // then
            assertErrorResult(resultActions, OPERATION_NOT_PERMITTED);
            verify(relationService, times(1)).confirmRelation(anyLong());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur est identifié mais que le token csrf est absent")
        void should_response_forbidden_when_user_is_authenticated_but_csrf_is_missing() throws Exception {
            //when - then
            mvc.perform(post(TEST_URI, 12L)).andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            //when - then
            mvc.perform(post(TEST_URI, 12L)).andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }
    }
}
