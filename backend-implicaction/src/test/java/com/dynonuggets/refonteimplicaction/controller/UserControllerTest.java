package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import com.dynonuggets.refonteimplicaction.service.UserService;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static com.dynonuggets.refonteimplicaction.utils.GroupUtils.generateRandomGroupDto;
import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUserDto;
import static java.lang.String.format;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    void resultActionsValidationForPageUser(final Page<UserDto> userMockPage, final ResultActions resultActions) throws Exception {
        final List<UserDto> pageElements = userMockPage.getContent();

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath(TOTAL_PAGES_PATH).value(userMockPage.getTotalPages()))
                .andExpect(jsonPath(TOTAL_ELEMENTS_PATH).value(userMockPage.getTotalElements()));

        for (int i = 0; i < pageElements.size(); i++) {
            UserDto userDto = pageElements.get(i);
            resultActionsValidationForSingleUser(userDto, resultActions, i);
        }
    }

    void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        resultActionsValidationForSingleUser(userDto, resultActions, null);
    }

    void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        String registeredAt = userDto.getRegisteredAt() != null ? userDto.getRegisteredAt().toString() : null;
        String activatedAt = userDto.getActivatedAt() != null ? userDto.getActivatedAt().toString() : null;
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.id", prefix), is(userDto.getId().intValue())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(userDto.getEmail())))
                .andExpect(jsonPath(format("%s.firstname", prefix), is(userDto.getFirstname())))
                .andExpect(jsonPath(format("%s.lastname", prefix), is(userDto.getLastname())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.url", prefix), is(userDto.getUrl())))
                .andExpect(jsonPath(format("%s.hobbies", prefix), is(userDto.getHobbies())))
                .andExpect(jsonPath(format("%s.purpose", prefix), is(userDto.getPurpose())))
                .andExpect(jsonPath(format("%s.presentation", prefix), is(userDto.getPresentation())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.expectation", prefix), is(userDto.getExpectation())))
                .andExpect(jsonPath(format("%s.contribution", prefix), is(userDto.getContribution())))
                .andExpect(jsonPath(format("%s.registeredAt", prefix), is(registeredAt)))
                .andExpect(jsonPath(format("%s.activatedAt", prefix), is(activatedAt)))
                .andExpect(jsonPath(format("%s.imageUrl", prefix), is(userDto.getImageUrl())));
    }

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


    // TODO: déplacer ces méthode dans une classe de tests pour un controller de relations quand celui-ci sera implémenté
    @Nested
    class RelationMethodsTest {
        @Test
        @WithMockUser
        void should_return_list_of_invitations_sent() throws Exception {
            // given
            UserDto receiver = UserDto.builder()
                    .id(3L)
                    .username("paul-sdv")
                    .firstname("Paul")
                    .lastname("Flu")
                    .email("paul@implicaction.fr")
                    .url("www.google.fr")
                    .hobbies("surf,gaming,judo")
                    .purpose("")
                    .registeredAt(Instant.now())
                    .activatedAt(Instant.now())
                    .roles(roles)
                    .active(true)
                    .build();

            User userTest = User.builder()
                    .id(10L)
                    .build();

            ArrayList<UserDto> friendsList = new ArrayList<>();
            friendsList.add(receiver);

            Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);

            given(authService.getCurrentUser()).willReturn(userTest);
            given(relationService.getSentFriendRequest(anyLong(), any())).willReturn(userPageMockResponse);

            // when
            final ResultActions resultActions = mvc.perform(
                            get(USER_BASE_URI + GET_FRIEND_REQUESTS_SENT_URI).contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());


            // then
            for (int i = 0; i < friendsList.size(); i++) {
                final String contentPath = format("$.content[%d]", i);
                resultActions.andExpect(jsonPath(contentPath + ".id", is(friendsList.get(i).getId().intValue())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".email", is(friendsList.get(i).getEmail())))
                        .andExpect(jsonPath(contentPath + ".firstname", is(friendsList.get(i).getFirstname())))
                        .andExpect(jsonPath(contentPath + ".lastname", is(friendsList.get(i).getLastname())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".url", is(friendsList.get(i).getUrl())))
                        .andExpect(jsonPath(contentPath + ".hobbies", is(friendsList.get(i).getHobbies())))
                        .andExpect(jsonPath(contentPath + ".purpose", is(friendsList.get(i).getPurpose())))
                        .andExpect(jsonPath(contentPath + ".presentation", is(friendsList.get(i).getPresentation())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".expectation", is(friendsList.get(i).getExpectation())))
                        .andExpect(jsonPath(contentPath + ".contribution", is(friendsList.get(i).getContribution())))
                        .andExpect(jsonPath(contentPath + ".registeredAt", is(friendsList.get(i).getRegisteredAt().toString())));
            }

            verify(relationService, times(1)).getSentFriendRequest(anyLong(), any());
        }

        @Test
        void should_response_forbidden_when_getting_all_sent_invitations_with_no_authentication() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_FRIEND_REQUESTS_SENT_URI)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @WithMockUser
        void should_return_list_of_invitations_received() throws Exception {
            // given
            UserDto sender = UserDto.builder()
                    .id(3L)
                    .username("paul-sdv")
                    .firstname("Paul")
                    .lastname("Flu")
                    .email("paul@implicaction.fr")
                    .url("www.google.fr")
                    .hobbies("surf,gaming,judo")
                    .purpose("")
                    .registeredAt(Instant.now())
                    .activatedAt(Instant.now())
                    .roles(roles)
                    .active(true)
                    .build();

            User userTest = User.builder()
                    .id(10L)
                    .build();

            ArrayList<UserDto> friendsList = new ArrayList<>();
            friendsList.add(sender);

            Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);

            given(authService.getCurrentUser()).willReturn(userTest);
            given(relationService.getReceivedFriendRequest(anyLong(), any())).willReturn(userPageMockResponse);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_FRIEND_REQUESTS_RECEIVED_URI)
            );

            // then
            resultActionsValidationForPageUser(new PageImpl<>(friendsList), resultActions);
            verify(relationService, times(1)).getReceivedFriendRequest(anyLong(), any());
        }

        @Test
        void should_response_forbidden_when_getting_all_received_invitations_with_no_authentication() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_FRIEND_REQUESTS_RECEIVED_URI)
            );

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(relationService);
        }

        @Test
        @WithMockUser
        void should_return_list_of_pending() throws Exception {
            // given
            UserDto sender = UserDto.builder()
                    .id(3L)
                    .username("paul-sdv")
                    .firstname("Paul")
                    .lastname("Flu")
                    .email("paul@implicaction.fr")
                    .url("www.google.fr")
                    .hobbies("surf,gaming,judo")
                    .purpose("")
                    .registeredAt(Instant.now())
                    .activatedAt(Instant.now())
                    .roles(roles)
                    .active(true)
                    .build();

            User userTest = User.builder()
                    .id(10L)
                    .build();

            ArrayList<UserDto> friendsList = new ArrayList<>();
            friendsList.add(sender);

            Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);

            given(authService.getCurrentUser()).willReturn(userTest);
            given(userService.getAllPendingActivationUsers(any())).willReturn(userPageMockResponse);

            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_PENDING_USER_URI)
            );

            // then
            resultActionsValidationForPageUser(userPageMockResponse, resultActions);
            verify(userService, times(1)).getAllPendingActivationUsers(any());
        }

        @Test
        void should_response_forbidden_when_getting_all_pending_user_with_no_authentication() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_PENDING_USER_URI)
            );

            // then
            resultActions.andDo(print()).andExpect(status().isForbidden());
            verifyNoInteractions(userService);

        }
    }

    @Nested
    @DisplayName("# updateImageProfile")
    class UpdateImageProfileTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec l'utilisateur mis à jour quand l'utilisateur est identifié")
        void should_response_ok_with_updated_user_when_updateImageProfile_and_authenticated() throws Exception {
            // given
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
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
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
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
    @DisplayName("# getAllFriends")
    class GetAllFriendsTests {
        @Test
        @WithMockUser
        void getAllFriendsForOneUserShouldReturnUserList() throws Exception {
            UserDto sender = UserDto.builder()
                    .id(2L)
                    .username("paul-sdv")
                    .firstname("Paul")
                    .lastname("Flu")
                    .email("paul@implicaction.fr")
                    .url("www.google.fr")
                    .hobbies("surf,gaming,judo")
                    .purpose("")
                    .registeredAt(Instant.now())
                    .activatedAt(Instant.now())
                    .roles(roles)
                    .active(true)
                    .build();

            UserDto receiver = UserDto.builder()
                    .id(3L)
                    .username("paul-sdv")
                    .firstname("Paul")
                    .lastname("Flu")
                    .email("paul@implicaction.fr")
                    .url("www.google.fr")
                    .hobbies("surf,gaming,judo")
                    .purpose("")
                    .registeredAt(Instant.now())
                    .activatedAt(Instant.now())
                    .roles(roles)
                    .active(true)
                    .build();

            List<UserDto> friendsList = new ArrayList<>();
            friendsList.add(receiver);

            Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);

            when(relationService.getAllFriendsByUserId(anyLong(), any())).thenReturn(userPageMockResponse);

            ResultActions actions = mvc.perform(get(USER_BASE_URI + GET_FRIEND_URI, sender.getId()).contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());

            for (int i = 0; i < friendsList.size(); i++) {
                final String contentPath = format("$.content[%d]", i);
                actions
                        .andExpect(jsonPath(contentPath + ".id", is(friendsList.get(i).getId().intValue())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".email", is(friendsList.get(i).getEmail())))
                        .andExpect(jsonPath(contentPath + ".firstname", is(friendsList.get(i).getFirstname())))
                        .andExpect(jsonPath(contentPath + ".lastname", is(friendsList.get(i).getLastname())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".url", is(friendsList.get(i).getUrl())))
                        .andExpect(jsonPath(contentPath + ".hobbies", is(friendsList.get(i).getHobbies())))
                        .andExpect(jsonPath(contentPath + ".purpose", is(friendsList.get(i).getPurpose())))
                        .andExpect(jsonPath(contentPath + ".presentation", is(friendsList.get(i).getPresentation())))
                        .andExpect(jsonPath(contentPath + ".username", is(friendsList.get(i).getUsername())))
                        .andExpect(jsonPath(contentPath + ".expectation", is(friendsList.get(i).getExpectation())))
                        .andExpect(jsonPath(contentPath + ".contribution", is(friendsList.get(i).getContribution())))
                        .andExpect(jsonPath(contentPath + ".registeredAt", is(friendsList.get(i).getRegisteredAt().toString())));
            }

            verify(relationService, times(1)).getAllFriendsByUserId(anyLong(), any());
        }

        @Test
        void getAllFriendsForOneUserShouldReturnForbidden() throws Exception {
            mvc.perform(get(USER_BASE_URI + GET_FRIEND_URI, 125L).contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isForbidden());

            verify(relationService, never()).getAllFriendsByUserId(anyLong(), any());
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
            List<GroupDto> mockedGroups = of(
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
            resultActions
                    .andExpect(status().isForbidden());
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
            mockedUserDto.setActivatedAt(null);
            mockedUserDto.setRegisteredAt(null);

            // when
            ResultActions resultActions = mvc.perform(
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
            ResultActions resultActions = mvc.perform(
                    put(USER_BASE_URI).content(gson.toJson(mockedUserDto)).contentType(APPLICATION_JSON).with(csrf())
            );

            // then
            resultActions
                    .andExpect(status().isForbidden());
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
            ResultActions resultActions = mvc.perform(
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
            ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI + GET_USER_URI, mockedUserDto.getId())
            );

            resultActions
                    .andExpect(status().isForbidden());
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
            resultActions.
                    andExpect(status().isForbidden());
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
            ResultActions resultActions = mvc.perform(
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
            ResultActions resultActions = mvc.perform(
                    get(USER_BASE_URI)
            );

            // then
            resultActions
                    .andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }

    }
}
