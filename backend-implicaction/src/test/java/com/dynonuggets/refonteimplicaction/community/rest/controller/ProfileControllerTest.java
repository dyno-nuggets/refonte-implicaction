package com.dynonuggets.refonteimplicaction.community.rest.controller;

import com.dynonuggets.refonteimplicaction.community.error.CommunityException;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.rest.controller.ControllerIntegrationTestBase;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.Charset;

import static com.dynonuggets.refonteimplicaction.community.error.CommunityErrorResult.PROFILE_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.util.CommunityMessages.PROFILE_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.util.ProfileUris.PROFILES_BASE_URI;
import static com.dynonuggets.refonteimplicaction.community.util.ProfileUtilTest.*;
import static java.lang.String.format;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    ProfileService profileService;

    @Nested
    @DisplayName("# getAllProfiles")
    class GetAllProfilesTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec la liste des profils quand l'utilisateur est identifié")
        void should_response_ok_with_all_profiles_when_getAllProfile_and_authenticated() throws Exception {
            // given
            final Page<ProfileDto> expectedPages = new PageImpl<>(of(generateRandomProfileDto(), generateRandomProfileDto()));
            given(profileService.getAllProfiles(any())).willReturn(expectedPages);

            // when
            final ResultActions resultActions = mvc.perform(get(PROFILES_BASE_URI));

            // then
            resultActionsValidationForPageProfile(expectedPages, resultActions);
            verify(profileService, times(1)).getAllProfiles(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas connecté")
        void should_response_forbidden_when_getAllProfile_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(PROFILES_BASE_URI));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(profileService);
        }
    }

    @Nested
    @DisplayName("# getProfileByUsername")
    class GetProfileByUsernameTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec le profil utilisateur correspondant quand il existe et que l'utilisateur est identifié")
        void should_response_ok_when_getProfileByUsername_and_username_exists_and_authenticated() throws Exception {
            // given
            final ProfileDto expectedProfile = generateRandomProfileDto();
            given(profileService.getByUsername(any())).willReturn(expectedProfile);

            // when
            final ResultActions resultActions = mvc.perform(get(format("%s/%s", PROFILES_BASE_URI, expectedProfile.getUsername())));

            // then
            resultActionsValidationForSingleProfile(expectedProfile, resultActions);
            verify(profileService, times(1)).getByUsername(any());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre NOT_FOUND quand le profil utilisateur n'existe pas et que l'utilisateur est identifié")
        void should_response_not_found_when_getProfileByUsername_and_username_not_exists_and_authenticated() throws Exception {
            // given
            final ProfileDto expectedProfile = generateRandomProfileDto();
            final String username = "notfoundusername";
            given(profileService.getByUsername(any())).willThrow(new CommunityException(PROFILE_NOT_FOUND, username));

            // when
            final ResultActions resultActions = mvc.perform(get(format("%s/%s", PROFILES_BASE_URI, expectedProfile.getUsername())));

            // then
            resultActions
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorMessage", is(format(PROFILE_NOT_FOUND_MESSAGE, username))))
                    .andExpect(jsonPath("$.errorCode", is(HttpStatus.SC_NOT_FOUND)));
            verify(profileService, times(1)).getByUsername(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(format("%s/%s", PROFILES_BASE_URI, "username")));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(profileService);
        }
    }

    @Nested
    @DisplayName("# updateProfile")
    class UpdateProfileTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec le profil modifié si l'utilisateur existe et est identifié")
        void should_response_ok_with_updated_profile_when_user_exists_and_authenticated() throws Exception {
            // given
            final ProfileUpdateRequest updateRequest = generateRandomProfileUpdateRequest();
            final ProfileDto expectedProfile = generateRandomProfileDto();
            expectedProfile.setUsername(updateRequest.getUsername());
            expectedProfile.setFirstname(updateRequest.getFirstname());
            expectedProfile.setLastname(updateRequest.getLastname());
            expectedProfile.setBirthday(updateRequest.getBirthday());
            expectedProfile.setHobbies(updateRequest.getHobbies());
            expectedProfile.setPurpose(updateRequest.getPurpose());
            expectedProfile.setPresentation(updateRequest.getPresentation());
            expectedProfile.setExpectation(updateRequest.getExpectation());
            expectedProfile.setContribution(updateRequest.getContribution());
            expectedProfile.setPhoneNumber(updateRequest.getPhoneNumber());
            given(profileService.updateProfile(any())).willReturn(expectedProfile);

            // when
            final ResultActions resultActions = mvc.perform(put(PROFILES_BASE_URI)
                    .with(csrf())
                    .content(toJson(updateRequest))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON));

            // then
            resultActionsValidationForSingleProfile(expectedProfile, resultActions);
            verify(profileService, times(1)).updateProfile(any());
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(put(PROFILES_BASE_URI)
                    .with(csrf())
                    .content(toJson(generateRandomProfileUpdateRequest()))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(profileService);
        }
    }
}