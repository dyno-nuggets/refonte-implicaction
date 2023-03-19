package com.dynonuggets.refonteimplicaction.core.user.controller;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.service.RelationService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.core.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.core.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.auth.utils.UserTestUtils.*;
import static com.dynonuggets.refonteimplicaction.core.user.domain.enums.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.core.user.util.UserUris.GET_PENDING_USER_URI;
import static com.dynonuggets.refonteimplicaction.core.user.util.UserUris.USER_BASE_URI;
import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("# getAllUser")
    class GetAllUserTests {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste paginées des utilisateurs quand l'utilisateur est identifié")
        void should_response_ok_with_userList_when_getAllUser_and_authenticated() throws Exception {
            // given
            given(userService.getAll(any(Pageable.class))).willReturn(mockedUserPage);

            // when
            final ResultActions resultActions = mvc.perform(get(USER_BASE_URI));

            // then
            resultActionsValidationForPageUser(mockedUserPage, resultActions);
            verify(userService, times(1)).getAll(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_getAllUser_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(USER_BASE_URI));

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
            final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_PENDING_USER_URI));

            // then
            resultActionsValidationForPageUser(expectedUsers, resultActions);
            verify(userService, times(1)).getAllPendingActivationUsers(any());
        }

        @Test
        @DisplayName("doit répondre OK avec la liste des utilisateurs dont le compte n'est pas encore activé")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(USER_BASE_URI + GET_PENDING_USER_URI));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(userService);
        }
    }
}
