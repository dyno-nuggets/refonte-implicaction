package com.dynonuggets.refonteimplicaction.community.group.controller;

import com.dynonuggets.refonteimplicaction.community.group.dto.GroupCreationRequest;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.service.GroupService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import lombok.Getter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.GROUP_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.USER_ALREADY_SUBSCRIBED_TO_GROUP;
import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupUris.*;
import static com.dynonuggets.refonteimplicaction.job.jobposting.utils.JobPostingUris.JOBS_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertErrorResultWithValue;
import static java.time.Instant.now;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GroupController.class)
class GroupControllerIntegrationTest extends ControllerIntegrationTestBase {

    @MockBean
    GroupService groupService;

    @Getter
    protected String baseUri = GROUPS_BASE_URI;

    @Nested
    @DisplayName("# createGroup - no image")
    class CreateGroupNoImageTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre CREATED quand l'utilisateur est identifié")
        void should_response_forbidden_when_create_group_with_no_image_with_no_authentication() throws Exception {
            // given
            final GroupCreationRequest groupCreationRequest = GroupCreationRequest.builder().name("name").description("description").build();
            final GroupDto expectedDto = GroupDto.builder().id(12L).name(groupCreationRequest.getName()).description(groupCreationRequest.getDescription()).createdAt(now()).creator("creator").build();
            given(groupService.createGroup(any(), any())).willReturn(expectedDto);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CREATE_NO_IMAGE))
                    .content(toJson(groupCreationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                    .andExpect(jsonPath("$.description").value(expectedDto.getDescription()))
                    .andExpect(jsonPath("$.creator").value(expectedDto.getCreator()))
                    .andExpect(jsonPath("$.createdAt").isNotEmpty())
                    .andExpect(jsonPath("$.imageUrl").isEmpty());
            verify(groupService, times(1)).createGroup(any(), any());
        }

        @Test
        @DisplayName("doit répondre forbidden sans image quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_create_group_with_no_image_and_with_no_authentication() throws Exception {
            // given
            final GroupCreationRequest groupCreationRequest = GroupCreationRequest.builder().name("name").description("description").build();

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(CREATE_NO_IMAGE))
                    .content(toJson(groupCreationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }

    @Nested
    @Disabled
    @DisplayName("# createGroup - with image")
    class CreateGroupWithImageTests {
        // TODO: ajouter le test d'envoi d'image
        @Test
        @DisplayName("doit répondre forbidden sans image quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_create_group_with_no_image_and_with_no_authentication() throws Exception {
            // given
            final GroupCreationRequest groupCreationRequest = GroupCreationRequest.builder().name("name").description("description").build();

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(GROUPS_BASE_URI))
                    .content(toJson(groupCreationRequest))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }

    @Nested
    @DisplayName("# subscribeGroup")
    class SubscribeGroupTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK quand un utilisateur identifié souscrit à un groupe dont il n'est pas membre")
        void should_response_ok_when_authenticated_user_subscribes_group_not_already_subscribed() throws Exception {
            // when
            final long groupId = 12L;
            willDoNothing().given(groupService).subscribeGroup(groupId);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(SUBSCRIBE_GROUP), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isOk());
            verify(groupService, times(1)).subscribeGroup(groupId);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre NOT_FOUND quand un utilisateur identifié souscrit à un groupe qui n'existe pas ou n'est pas activé")
        void should_response_notfound_when_authenticated_user_subscribes_group_and_group_not_exists() throws Exception {
            // when
            final long groupId = 12L;
            willThrow(new EntityNotFoundException(GROUP_NOT_FOUND, Long.toString(groupId))).given(groupService).subscribeGroup(groupId);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(SUBSCRIBE_GROUP), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            assertErrorResultWithValue(resultActions, GROUP_NOT_FOUND, Long.toString(groupId));
            verify(groupService, times(1)).subscribeGroup(groupId);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre CONFLICT quand un utilisateur identifié souscrit à un groupe dont il est déjà membre")
        void should_response_conflict_when_authenticated_user_subscribes_group_and_user_already_subscribed() throws Exception {
            // when
            final long groupId = 12L;
            final String groupName = "group not found";
            willThrow(new EntityNotFoundException(USER_ALREADY_SUBSCRIBED_TO_GROUP, groupName)).given(groupService).subscribeGroup(groupId);

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(SUBSCRIBE_GROUP), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            assertErrorResultWithValue(resultActions, USER_ALREADY_SUBSCRIBED_TO_GROUP, groupName);
            verify(groupService, times(1)).subscribeGroup(groupId);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN quand un utilisateur identifié souscrit à un groupe sans csrf")
        void should_response_forbidden_when_authenticated_but_no_csrf() throws Exception {
            // given
            final long groupId = 12L;

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(SUBSCRIBE_GROUP), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand un utilisateur non identifié souscrit à un groupe")
        void should_response_forbidden_when_no_authenticated() throws Exception {
            // given
            final long groupId = 12L;

            // when
            final ResultActions resultActions = mvc.perform(post(getFullPath(SUBSCRIBE_GROUP), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }

    @Nested
    @DisplayName("# getAllEnabledGroups")
    class GetAllEnabledGroupsTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste des groupes actifs quand l'utilisateur est identifié")
        void should_response_ok_with_list_of_subreddit_when_authentication() throws Exception {
            final Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
            // given
            final Page<GroupDto> subreddits = new PageImpl<>(of(
                    GroupDto.builder().build(),
                    GroupDto.builder().build(),
                    GroupDto.builder().build()
            ));
            given(groupService.getAllEnabledGroups(pageable)).willReturn(subreddits);

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_VALIDATED_GROUPS_URI))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.totalPages").value(subreddits.getTotalPages()))
                    .andExpect(jsonPath("$.totalElements").value(subreddits.getTotalElements()));
            verify(groupService, times(1)).getAllEnabledGroups(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_no_authentication() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(JOBS_BASE_URI).contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }


    @Nested
    @DisplayName("# getAllPendingGroups")
    class GetAllPendingGroups {
        @Test
        @WithMockUser(roles = {"ADMIN"})
        @DisplayName("doit répondre OK quand l'utilisateur est ADMIN")
        void should_response_ok_with_all_pending_groups_when_admin() throws Exception {
            //given
            final List<GroupDto> groupDtos = of(
                    GroupDto.builder().id(1L).enabled(false).build(),
                    GroupDto.builder().id(2L).enabled(false).build(),
                    GroupDto.builder().id(3L).enabled(false).build()
            );
            final Page<GroupDto> groupPageMockResponse = new PageImpl<>(groupDtos);
            given(groupService.getAllPendingGroups(any())).willReturn(groupPageMockResponse);

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PENDING_GROUP_URI))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON));

            for (int i = 0; i < groupDtos.size(); i++) {
                final String contentPath = String.format("$.content[%d]", i);
                resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(groupDtos.get(i).getId()))));
                resultActions.andExpect(jsonPath(contentPath + ".enabled", is(groupDtos.get(i).isEnabled())));
            }
            verify(groupService, times(1)).getAllPendingGroups(any());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre forbidden quand l'utilisateur n'est pas ADMIN")
        void should_response_forbidden_when_pending_groups_and_not_ADMIN() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PENDING_GROUP_URI))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }

        @Test
        @DisplayName("doit répondre forbidden quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_pending_groups_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PENDING_GROUP_URI))
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andDo(print()).andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }

    @Nested
    @DisplayName("# enableGroup")
    class EnableGroupTests {
        @Test
        @WithMockUser(roles = {"ADMIN"})
        @DisplayName("doit répondre OK avec le groupe activé quand l'utilisateur est ADMIN avec csrf")
        void should_response_ok_with_updated_group_when_user_is_admin_with_csrf() throws Exception {
            // given
            final long groupId = 12L;
            given(groupService.enableGroup(groupId)).willReturn(GroupDto.builder().id(groupId).enabled(true).build());

            // when
            final ResultActions resultActions = mvc.perform(patch(getFullPath(ENABLE_GROUP_URI), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is((int) groupId)))
                    .andExpect(jsonPath("$.enabled", is(true)));
            verify(groupService, times(1)).enableGroup(groupId);
        }

        @Test
        @WithMockUser(roles = {"ADMIN"})
        @DisplayName("doit répondre OK avec le groupe activé quand l'utilisateur est ADMIN sans csrf")
        void should_response_ok_with_updated_group_when_user_is_admin() throws Exception {
            // given
            final long groupId = 12L;
            given(groupService.enableGroup(groupId)).willReturn(GroupDto.builder().id(groupId).enabled(true).build());

            // when
            final ResultActions resultActions = mvc.perform(patch(getFullPath(ENABLE_GROUP_URI), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre FORBIDDEN l'utilisateur n'est pas ADMIN")
        void should_response_ok_with_updated_group_when_user_is_not_admin() throws Exception {
            // given
            final long groupId = 12L;
            given(groupService.enableGroup(groupId)).willReturn(GroupDto.builder().id(groupId).enabled(true).build());

            // when
            final ResultActions resultActions = mvc.perform(patch(getFullPath(ENABLE_GROUP_URI), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN l'utilisateur n'est pas identifié")
        void should_response_ok_with_updated_group_when_user_is_not_authenticated() throws Exception {
            // given
            final long groupId = 12L;
            given(groupService.enableGroup(groupId)).willReturn(GroupDto.builder().id(groupId).enabled(true).build());

            // when
            final ResultActions resultActions = mvc.perform(patch(getFullPath(ENABLE_GROUP_URI), groupId)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(groupService);
        }
    }
}
