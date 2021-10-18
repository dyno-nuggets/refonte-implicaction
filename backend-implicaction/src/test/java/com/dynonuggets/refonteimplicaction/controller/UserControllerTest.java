package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import com.dynonuggets.refonteimplicaction.service.RelationService;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import com.dynonuggets.refonteimplicaction.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    protected MockMvc mvc;
    List<UserDto> userDtos;
    ArrayList<String> roles = new ArrayList<>();
    @InjectMocks
    private UserController userController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private UserService userService;
    @MockBean
    private RelationService relationService;
    @MockBean
    private AuthService authService;
    @MockBean
    private RelationRepository relationRepository;

    @BeforeEach
    protected void setUp() {
        roles.add(RoleEnum.USER.getLongName());
        userDtos = Arrays.asList(
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
        ResultActions actions;

        when(userService.getAll(pageable)).thenReturn(userPageMockResponse);
        actions = mvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.totalPages").value(userPageMockResponse.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(userDtos.size()));

        for (int i = 0; i < userDtos.size(); i++) {
            actions.andExpect(jsonPath("$.content[" + i + "].id", Matchers.is(Math.toIntExact(userDtos.get(i).getId()))))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].email", Matchers.is(userDtos.get(i).getEmail())))
                    .andExpect(jsonPath("$.content[" + i + "].firstname", Matchers.is(userDtos.get(i).getFirstname())))
                    .andExpect(jsonPath("$.content[" + i + "].lastname", Matchers.is(userDtos.get(i).getLastname())))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].url", Matchers.is(userDtos.get(i).getUrl())))
                    .andExpect(jsonPath("$.content[" + i + "].hobbies", Matchers.is(userDtos.get(i).getHobbies())))
                    .andExpect(jsonPath("$.content[" + i + "].purpose", Matchers.is(userDtos.get(i).getPurpose())))
                    .andExpect(jsonPath("$.content[" + i + "].presentation", Matchers.is(userDtos.get(i).getPresentation())))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(userDtos.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].expectation", Matchers.is(userDtos.get(i).getExpectation())))
                    .andExpect(jsonPath("$.content[" + i + "].contribution", Matchers.is(userDtos.get(i).getContribution())))
                    .andExpect(jsonPath("$.content[" + i + "].registeredAt", Matchers.is(userDtos.get(i).getRegisteredAt().toString())));
        }
        actions.andReturn();
    }

    @Test
    void getAllWithoutJwtShouldBeForbidden() throws Exception {
        int first = 0;
        int rows = 10;
        String sortOrder = "ASC";
        String sortBy = "id";

        Page<UserDto> userPageMockResponse = new PageImpl<>(userDtos);
        Pageable pageable = PageRequest.of(first, rows, Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));

        when(userService.getAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mvc.perform(MockMvcRequestBuilders.get("/api/job-postings")
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
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
        mvc.perform(MockMvcRequestBuilders.get("/api/users/" + userDto.getId())
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(Math.toIntExact(userDto.getId()))))
                .andExpect(jsonPath("$.username", Matchers.is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", Matchers.is(userDto.getEmail())))
                .andExpect(jsonPath("$.firstname", Matchers.is(userDto.getFirstname())))
                .andExpect(jsonPath("$.lastname", Matchers.is(userDto.getLastname())))
                .andExpect(jsonPath("$.username", Matchers.is(userDto.getUsername())))
                .andExpect(jsonPath("$.url", Matchers.is(userDto.getUrl())))
                .andExpect(jsonPath("$.hobbies", Matchers.is(userDto.getHobbies())))
                .andExpect(jsonPath("$.purpose", Matchers.is(userDto.getPurpose())))
                .andExpect(jsonPath("$.presentation", Matchers.is(userDto.getPresentation())))
                .andExpect(jsonPath("$.username", Matchers.is(userDto.getUsername())))
                .andExpect(jsonPath("$.expectation", Matchers.is(userDto.getExpectation())))
                .andExpect(jsonPath("$.contribution", Matchers.is(userDto.getContribution())))
                .andExpect(jsonPath("$.registeredAt", Matchers.is(userDto.getRegisteredAt().toString())))
                .andExpect(jsonPath("$.activatedAt", Matchers.is(userDto.getActivatedAt().toString())))
                .andReturn();
    }

    @Test
    void getUserByIdWithoutJwtShouldBeForbidden() throws Exception {

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
        mvc.perform(MockMvcRequestBuilders.get("/api/users/" + userDto.getId())
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser
    void getAllFriendsForOneUserShouldReturnUserList() throws Exception {

        int first = 0;
        int rows = 10;

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


        ArrayList<UserDto> friendsList = new ArrayList<>();
        friendsList.add(receiver);

        Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);
        Pageable pageable = PageRequest.of(first, rows);
        ResultActions actions;

        when(relationService.getAllFriendsByUserId(sender.getId(), pageable)).thenReturn(userPageMockResponse);
        actions = mvc.perform(MockMvcRequestBuilders.get("/api/users/" + sender.getId() + "/friends")
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        for (int i = 0; i < friendsList.size(); i++) {
            actions.andExpect(jsonPath("$.content[" + i + "].id", Matchers.is(Math.toIntExact(friendsList.get(i).getId()))))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(friendsList.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].email", Matchers.is(friendsList.get(i).getEmail())))
                    .andExpect(jsonPath("$.content[" + i + "].firstname", Matchers.is(friendsList.get(i).getFirstname())))
                    .andExpect(jsonPath("$.content[" + i + "].lastname", Matchers.is(friendsList.get(i).getLastname())))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(friendsList.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].url", Matchers.is(friendsList.get(i).getUrl())))
                    .andExpect(jsonPath("$.content[" + i + "].hobbies", Matchers.is(friendsList.get(i).getHobbies())))
                    .andExpect(jsonPath("$.content[" + i + "].purpose", Matchers.is(friendsList.get(i).getPurpose())))
                    .andExpect(jsonPath("$.content[" + i + "].presentation", Matchers.is(friendsList.get(i).getPresentation())))
                    .andExpect(jsonPath("$.content[" + i + "].username", Matchers.is(friendsList.get(i).getUsername())))
                    .andExpect(jsonPath("$.content[" + i + "].expectation", Matchers.is(friendsList.get(i).getExpectation())))
                    .andExpect(jsonPath("$.content[" + i + "].contribution", Matchers.is(friendsList.get(i).getContribution())))
                    .andExpect(jsonPath("$.content[" + i + "].registeredAt", Matchers.is(friendsList.get(i).getRegisteredAt().toString())));
        }

        actions.andReturn();
    }

    @Test
    void getAllFriendsForOneUserShouldReturnForbidden() throws Exception {

        int first = 0;
        int rows = 10;

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

        RelationsDto relationsDto = RelationsDto.builder()
                .id(1L)
                .receiver(receiver)
                .sender(sender)
                .confirmedAt(Instant.now())
                .sentAt(Instant.now())
                .build();

        ArrayList<UserDto> friendsList = new ArrayList<>();
        friendsList.add(receiver);

        Page<UserDto> userPageMockResponse = new PageImpl<>(friendsList);
        Pageable pageable = PageRequest.of(first, rows);

        when(relationService.getAllFriendsByUserId(sender.getId(), pageable)).thenReturn(userPageMockResponse);
        mvc.perform(MockMvcRequestBuilders.get("/api/users/" + sender.getId() + "/friends")
                .accept(MediaType.ALL).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }


}