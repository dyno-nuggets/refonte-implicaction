package com.dynonuggets.refonteimplicaction.auth.rest.controller;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.auth.service.UserService;
import com.dynonuggets.refonteimplicaction.community.rest.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.service.RelationService;
import com.dynonuggets.refonteimplicaction.core.rest.controller.ControllerIntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtilTest.*;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.*;
import static com.dynonuggets.refonteimplicaction.utils.GroupUtils.generateRandomGroupDto;
import static java.lang.String.format;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ControllerIntegrationTestBase {

    User mockedUser;
    UserDto mockedUserDto;
    List<UserDto> mockedUserDtos;
    Page<UserDto> mockedUserPage;
    ArrayList<String> roles = new ArrayList<>();

    @MockBean
    UserService userService;
    @MockBean
    RelationService relationService;
    @MockBean
    AuthService authService;

    @BeforeEach
    void setUp() {
        roles.add(USER.getLongName());
        mockedUser = generateRandomUser();
        mockedUserDtos = of(
                generateRandomUserDto(),
                generateRandomUserDto(),
                generateRandomUserDto(),
                generateRandomUserDto()
        );

        mockedUserDto = generateRandomUserDto();
        mockedUserPage = new PageImpl<>(mockedUserDtos);
    }

    @Nested
    @DisplayName("# updateImageProfile")
    class UpdateImageProfileTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec l'utilisateur mis à jour quand l'utilisateur est identifié")
        void should_response_ok_with_updated_user_when_updateImageProfile_and_authenticated() throws Exception {
            // given
            final MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "user-file",
                    "test.png",
                    IMAGE_PNG_VALUE,
                    "test data".getBytes()
            );

            given(userService.updateImageProfile(any())).willReturn(mockedUserDto);

            // when
            final ResultActions resultActions = mvc.perform(
                    multipart(USER_BASE_URI + SET_USER_IMAGE).file("file", mockMultipartFile.getBytes()).with(csrf())
            );

            // then
            resultActionsValidationForSingleUser(mockedUserDto, resultActions);
            verify(userService, times(1)).updateImageProfile(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_updateImageProfile_with_no_authentication() throws Exception {
            // given
            final MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "user-file",
                    "test.png",
                    IMAGE_PNG_VALUE,
                    "test data".getBytes()
            );

            // when
            final ResultActions resultActions = mvc.perform(
                    multipart(USER_BASE_URI + SET_USER_IMAGE).file("file", mockMultipartFile.getBytes())
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }


    @Nested
    @DisplayName("# getUserGroups")
    class GetUserGroupsTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec une liste non paginée de groupes quand l'utilisateur est identifié")
        void should_response_ok_with_groupList_when_getUserGroups_and_authenticated() throws Exception {
            // given
            final List<GroupDto> mockedGroups = of(
                    generateRandomGroupDto(),
                    generateRandomGroupDto(),
                    generateRandomGroupDto()
            );
            final long userId = mockedUserDto.getId();
            given(userService.getUserGroups(userId)).willReturn(mockedGroups);

            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_USER_GROUPS_URI, userId)
            );

            resultActions
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON));

            for (int i = 0; i < mockedGroups.size(); i++) {
                final String contentPath = format("$[%d]", i);
                resultActions
                        .andExpect(jsonPath(contentPath + ".id", is(mockedGroups.get(i).getId().intValue())))
                        .andExpect(jsonPath(contentPath + ".description", is(mockedGroups.get(i).getDescription())))
                        .andExpect(jsonPath(contentPath + ".numberOfPosts", is(mockedGroups.get(i).getNumberOfPosts())))
                        .andExpect(jsonPath(contentPath + ".imageUrl", is(mockedGroups.get(i).getImageUrl())))
                        .andExpect(jsonPath(contentPath + ".name", is(mockedGroups.get(i).getName())))
                        .andExpect(jsonPath(contentPath + ".numberOfUsers", is(mockedGroups.get(i).getNumberOfUsers())));
            }

            verify(userService, times(1)).getUserGroups(userId);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_getUserGroups_and_no_authenticated() throws Exception {
            // given
            final long userId = mockedUserDto.getId();

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_USER_GROUPS_URI, userId)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }

    @Nested
    @DisplayName("# updateUser")
    class UpdateUserTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec l'utilisateur transmis quand l'utilisateur est identifié")
        void should_response_ok_with_user_when_updateUser_and_authenticated() throws Exception {
            // given
            given(userService.updateUser(mockedUserDto)).willReturn(mockedUserDto);
            // il faut mettre toutes les dates à null, sinon l'objet envoyé n'est pas reconnu comme un UserDto et la
            // réponse la requête est traitée en 400
            mockedUserDto.setBirthday(null);
            mockedUserDto.setRegisteredAt(null);

            // when
            final ResultActions resultActions = mvc.perform(
                    put(USER_BASE_URI).content(gson.toJson(mockedUserDto)).contentType(APPLICATION_JSON).with(csrf())
            );

            // then
            resultActionsValidationForSingleUser(mockedUserDto, resultActions);
            verify(userService, times(1)).updateUser(mockedUserDto);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_updateUser_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    put(USER_BASE_URI).content(gson.toJson(mockedUserDto)).contentType(APPLICATION_JSON).with(csrf())
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }

    @Nested
    @DisplayName("# getUserProfile")
    class GetUserProfileTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec un utilisateur quand l'utilisateur est identifié")
        void should_response_ok_whith_user_when_getUserProfile_and_authenticated() throws Exception {
            // given
            given(userService.getUserById(anyLong())).willReturn(mockedUserDto);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_USER_URI, mockedUserDto.getId())
            );

            // then
            resultActionsValidationForSingleUser(mockedUserDto, resultActions);
            verify(userService, times(1)).getUserById(mockedUserDto.getId());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_getUserProfile_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_USER_URI, mockedUserDto.getId())
            );

            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }

    @Nested
    @DisplayName("# getAllCommunity")
    class GetAllCommunityTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste paginée d'utilisateurs quand l'utilisateur principal est identifié")
        void should_response_ok_with_usersList_when_getAllComunity_and_authenticated() throws Exception {
            // given
            given(authService.getCurrentUser()).willReturn(mockedUser);
            given(userService.getAllCommunity(any())).willReturn(mockedUserPage);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_COMMUNITY_URI)
            );

            //then
            resultActionsValidationForPageUser(mockedUserPage, resultActions);
            verify(userService, times(1)).getAllCommunity(DEFAULT_PAGEABLE);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_getAllComunity_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_COMMUNITY_URI)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }

    @Nested
    @DisplayName("# getAllUser")
    class GetAllUserTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste paginées des utilisateurs quand l'utilisateur est identifié")
        void should_response_ok_with_userList_when_getAllUser_and_authenicated() throws Exception {
            // given
            given(userService.getAll(any(Pageable.class))).willReturn(mockedUserPage);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI)
            );

            // then
            resultActionsValidationForPageUser(mockedUserPage, resultActions);
            verify(userService, times(1)).getAll(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_getAllUser_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }

    }

    @Nested
    @DisplayName("# getAllPendingUsers")
    class GetAllPendingUsers {
        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("doit répondre OK avec la liste des utilisateurs dont le compte n'est pas encore activé quand l'utilisateur est admin")
        void should_response_ok_with_list_of_non_activated_users_when_user_is_authenticated_as_admin() throws Exception {
            // given
            final Page<UserDto> expectedUsers = new PageImpl<>(of(generateRandomUserDto(), generateRandomUserDto()));
            given(userService.getAllPendingActivationUsers(any())).willReturn(expectedUsers);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_PENDING_USER_URI)
            );

            // then
            resultActionsValidationForPageUser(expectedUsers, resultActions);
            verify(userService, times(1)).getAllPendingActivationUsers(any());
        }

        @Test
        @DisplayName("doit répondre OK avec la liste des utilisateurs dont le compte n'est pas encore activé")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_PENDING_USER_URI)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }
}
