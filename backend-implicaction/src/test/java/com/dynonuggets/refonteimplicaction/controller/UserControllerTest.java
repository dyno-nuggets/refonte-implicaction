package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.GroupService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import com.dynonuggets.refonteimplicaction.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ControllerIntegrationTestBase {

    List<UserDto> userDtos;
    ArrayList<String> roles = new ArrayList<>();

    @MockBean
    UserService userService;

    @MockBean
    RelationService relationService;

    @MockBean
    AuthService authService;

    @MockBean
    GroupService groupService;

    @MockBean
    RelationRepository relationRepository;

    @BeforeEach
    void setUp() {
        roles.add(RoleEnum.USER.getLongName());
        userDtos = asList(
                UserDto.builder().id(1L).username("mathusha-sdv").firstname("Mathusha").lastname("Thiru").email("mathu@implicaction.fr").url("www.google.fr").hobbies("surf,gaming,judo").purpose("").registeredAt(Instant.now()).activatedAt(Instant.now()).relationTypeOfCurrentUser(RelationTypeEnum.NONE).roles(roles).active(true).build(),
                UserDto.builder().id(2L).username("paul-sdv").firstname("Paul").lastname("Flu").email("paul@implicaction.fr").url("www.google.fr").hobbies("surf,gaming,judo").purpose("").registeredAt(Instant.now()).activatedAt(Instant.now()).relationTypeOfCurrentUser(RelationTypeEnum.NONE).roles(roles).active(true).build(),
                UserDto.builder().id(3L).username("paul-sdv").firstname("Paul").lastname("Flu").email("paul@implicaction.fr").url("www.google.fr").hobbies("surf,gaming,judo").purpose("").registeredAt(Instant.now()).relationTypeOfCurrentUser(RelationTypeEnum.NONE).roles(roles).active(false).build()
        );

    }

    @WithMockUser
    @Test
    void getAllUserListShouldListAllUser() throws Exception {
        int first = 0;
        int rows = 10;

        Page<UserDto> userPageMockResponse = new PageImpl<>(userDtos);
        Pageable pageable = PageRequest.of(first, rows);

        when(userService.getAll(pageable)).thenReturn(userPageMockResponse);
        ResultActions actions = mvc.perform(get(USER_BASE_URI).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(userPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(userDtos.size()));

        for (int i = 0; i < userDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", is(userDtos.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".username", is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath(contentPath + ".email", is(userDtos.get(i).getEmail())))
                    .andExpect(jsonPath(contentPath + ".firstname", is(userDtos.get(i).getFirstname())))
                    .andExpect(jsonPath(contentPath + ".lastname", is(userDtos.get(i).getLastname())))
                    .andExpect(jsonPath(contentPath + ".username", is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath(contentPath + ".url", is(userDtos.get(i).getUrl())))
                    .andExpect(jsonPath(contentPath + ".hobbies", is(userDtos.get(i).getHobbies())))
                    .andExpect(jsonPath(contentPath + ".purpose", is(userDtos.get(i).getPurpose())))
                    .andExpect(jsonPath(contentPath + ".presentation", is(userDtos.get(i).getPresentation())))
                    .andExpect(jsonPath(contentPath + ".username", is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath(contentPath + ".expectation", is(userDtos.get(i).getExpectation())))
                    .andExpect(jsonPath(contentPath + ".contribution", is(userDtos.get(i).getContribution())))
                    .andExpect(jsonPath(contentPath + ".registeredAt", is(userDtos.get(i).getRegisteredAt().toString())));
        }

        verify(userService, times(1)).getAll(any());
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        mvc.perform(get(USER_BASE_URI).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(userService, never()).getAll(any());
    }

    @Test
    @WithMockUser
    void getUserByIdShouldReturnOneUser() throws Exception {
        UserDto userDto = UserDto.builder()
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

        when(userService.getUserById(userDto.getId())).thenReturn(userDto);

        mvc.perform(get(USER_BASE_URI + GET_USER_URI, userDto.getId()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.firstname", is(userDto.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(userDto.getLastname())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.url", is(userDto.getUrl())))
                .andExpect(jsonPath("$.hobbies", is(userDto.getHobbies())))
                .andExpect(jsonPath("$.purpose", is(userDto.getPurpose())))
                .andExpect(jsonPath("$.presentation", is(userDto.getPresentation())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.expectation", is(userDto.getExpectation())))
                .andExpect(jsonPath("$.contribution", is(userDto.getContribution())))
                .andExpect(jsonPath("$.registeredAt", is(userDto.getRegisteredAt().toString())))
                .andExpect(jsonPath("$.activatedAt", is(userDto.getActivatedAt().toString())));

        verify(userService, times(1)).getUserById(any());

    }

    @Test
    void getUserByIdWithoutJwtShouldBeForbidden() throws Exception {
        mvc.perform(get(USER_BASE_URI + GET_USER_URI, 125L).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(userService, never()).getUserById(125L);
    }

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
                .andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < friendsList.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            actions.andExpect(jsonPath(contentPath + ".id", is(friendsList.get(i).getId().intValue())))
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
        ResultActions actions;

        given(authService.getCurrentUser()).willReturn(userTest);
        given(relationService.getSentFriendRequest(anyLong(), any())).willReturn(userPageMockResponse);

        // when
        final ResultActions resultActions = mvc.perform(
                        get(USER_BASE_URI + GET_FRIEND_REQUESTS_SENT_URI).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());


        // then
        for (int i = 0; i < friendsList.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
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
        final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_FRIEND_REQUESTS_SENT_URI)
                .contentType(APPLICATION_JSON)).andDo(print());
        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(relationService, never()).getSentFriendRequest(anyLong(), any());

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
                        get(USER_BASE_URI + GET_FRIEND_REQUESTS_RECEIVED_URI).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());


        // then
        for (int i = 0; i < friendsList.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
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

        verify(relationService, times(1)).getReceivedFriendRequest(anyLong(), any());
    }

    @Test
    void should_response_forbidden_when_getting_all_received_invitations_with_no_authentication() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_FRIEND_REQUESTS_RECEIVED_URI)
                .contentType(APPLICATION_JSON)).andDo(print());
        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(relationService, never()).getReceivedFriendRequest(anyLong(), any());

    }

    @Test
    void should_response_forbidden_when_updating_user_with_no_authentication() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(put(USER_BASE_URI)
                .contentType(APPLICATION_JSON)).andDo(print());
        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).updateUser(any());

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
                        get(USER_BASE_URI + GET_PENDING_USER_URI).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());


        // then
        for (int i = 0; i < friendsList.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(friendsList.get(i).getId().intValue()))))
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

        verify(userService, times(1)).getAllPendingActivationUsers(any());
    }

    @Test
    void should_response_forbidden_when_getting_all_pending_user_with_no_authentication() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_PENDING_USER_URI)
                .contentType(APPLICATION_JSON)).andDo(print());
        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).getAllPendingActivationUsers(any());

    }

    @Test
    @WithMockUser
    void should_return_list_of_community() throws Exception {
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
        given(userService.getAllCommunity(any())).willReturn(userPageMockResponse);

        // when
        final ResultActions resultActions = mvc.perform(
                        get(USER_BASE_URI + GET_COMMUNITY_URI).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());


        // then
        for (int i = 0; i < friendsList.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(friendsList.get(i).getId().intValue()))))
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

        verify(userService, times(1)).getAllCommunity(any());
    }

    @Test
    void should_response_forbidden_when_getting_all_community_with_no_authentication() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_COMMUNITY_URI)
                .contentType(APPLICATION_JSON)).andDo(print());
        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).getAllCommunity(any());
    }

    @Test
    @WithMockUser
    void should_update_user() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .id(123L)
                .username("fdklsq")
                .firstname("fdsqf")
                .lastname("fkdlsqmf")
                .email("mail@mail.com")
                .hobbies("jdksdjksql")
                .build();
        String json = gson.toJson(userDto);
        given(userService.updateUser(userDto)).willReturn(userDto);

        // when
        ResultActions resultActions = mvc.perform(
                put(USER_BASE_URI).content(json).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.firstname", is(userDto.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(userDto.getLastname())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.url", is(userDto.getUrl())))
                .andExpect(jsonPath("$.hobbies", is(userDto.getHobbies())))
                .andExpect(jsonPath("$.purpose", is(userDto.getPurpose())))
                .andExpect(jsonPath("$.presentation", is(userDto.getPresentation())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.expectation", is(userDto.getExpectation())))
                .andExpect(jsonPath("$.contribution", is(userDto.getContribution())));
    }

    @Test
    @WithMockUser
    void should_save_user_image() throws Exception {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.png",
                IMAGE_PNG_VALUE,
                "test data".getBytes()
        );

        UserDto userDto = UserDto.builder().id(123L).imageUrl("http://image.adresse/imagekey").build();

        given(userService.updateImageProfile(any())).willReturn(userDto);

        // when
        final ResultActions resultActions = mvc.perform(
                multipart(USER_BASE_URI + SET_USER_IMAGE).file("file", mockMultipartFile.getBytes()).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl").isNotEmpty());

        verify(userService, times(1)).updateImageProfile(any());
    }

    @Test
    void should_return_forbidden_when_save_user_image_with_no_authentication() throws Exception {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.png",
                IMAGE_PNG_VALUE,
                "test data".getBytes()
        );

        // when
        final ResultActions resultActions = mvc
                .perform(multipart(USER_BASE_URI + SET_USER_IMAGE).file("file", mockMultipartFile.getBytes()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isForbidden());

        verify(userService, never()).updateImageProfile(any());
    }

    @Test
    @WithMockUser
    void getAllGroupsByUser() throws Exception {
        UserDto user = UserDto.builder().id(1L).build();
        ArrayList<UserDto> userList = new ArrayList<>();
        userList.add(user);

        GroupDto group = GroupDto.builder().id(1L).name("ile-de-france").description("communauté ile de france").numberOfPosts(0).imageUrl("").numberOfUsers(userList.size()).build();

        ArrayList<GroupDto> groupList = new ArrayList<>();
        groupList.add(group);
        given(userService.getUserGroups(anyLong())).willReturn(groupList);

        final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_USER_GROUPS_URI, user.getId()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < groupList.size(); i++) {
            final String contentPath = String.format("$[%d]", i);

            resultActions
                    .andExpect(jsonPath(contentPath + ".id", is(groupList.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".description", is(groupList.get(i).getDescription())))
                    .andExpect(jsonPath(contentPath + ".numberOfPosts", is(groupList.get(i).getNumberOfPosts())))
                    .andExpect(jsonPath(contentPath + ".imageUrl", is(groupList.get(i).getImageUrl())))
                    .andExpect(jsonPath(contentPath + ".name", is(groupList.get(i).getName())))
                    .andExpect(jsonPath(contentPath + ".numberOfUsers", is(groupList.get(i).getNumberOfUsers())));
        }
        verify(userService, times(1)).getUserGroups(anyLong());
    }
}
