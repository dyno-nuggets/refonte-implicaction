package com.dynonuggets.refonteimplicaction.community.profile.controller;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.profile.dto.enums.RelationCriteriaEnum;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import lombok.Getter;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.Charset;
import java.util.Set;

import static com.dynonuggets.refonteimplicaction.community.profile.error.ProfileErrorResult.PROFILE_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.*;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.GET_PROFILE_BY_USERNAME;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileUris.PROFILES_BASE_URI;
import static com.dynonuggets.refonteimplicaction.filemanagement.utils.FileUris.POST_PROFILE_AVATAR;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertErrorResultFieldValidation;
import static java.lang.String.format;
import static java.util.List.of;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = PROFILES_BASE_URI;

    @MockBean
    AuthService authService;

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
            given(profileService.getAllProfiles(any(RelationCriteriaEnum.class), any(Pageable.class))).willReturn(expectedPages);

            // when
            final ResultActions resultActions = mvc.perform(get(baseUri)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActionsValidationForPageProfile(expectedPages, resultActions);
            verify(profileService, times(1)).getAllProfiles(any(RelationCriteriaEnum.class), any(Pageable.class));
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas connecté")
        void should_response_forbidden_when_getAllProfile_and_not_authenticated() throws Exception {
            // when
            final ResultActions resultActions = mvc.perform(get(baseUri)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

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
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PROFILE_BY_USERNAME), expectedProfile.getUsername())
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

            // then
            resultActionsAssertionsForSingleProfile(expectedProfile, resultActions);
            verify(profileService, times(1)).getByUsername(any());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre NOT_FOUND quand le profil utilisateur n'existe pas et que l'utilisateur est identifié")
        void should_response_not_found_when_getProfileByUsername_and_username_not_exists_and_authenticated() throws Exception {
            // given
            final String username = "notfoundusername";
            given(profileService.getByUsername(any())).willThrow(new EntityNotFoundException(PROFILE_NOT_FOUND, username));

            // when
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PROFILE_BY_USERNAME), username)
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

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
            final ResultActions resultActions = mvc.perform(get(getFullPath(GET_PROFILE_BY_USERNAME), "username")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON));

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
        @DisplayName("doit répondre OK avec le profil modifié quand l'utilisateur existe et est identifié et que l'objet de requête est valide")
        void should_response_ok_with_updated_profile_when_user_exists_and_authenticated() throws Exception {
            // given
            final ProfileUpdateRequest updateRequest = generateRandomProfileUpdateRequest();
            final ProfileDto expectedProfile = generateRandomProfileDto();
            expectedProfile.setUsername(updateRequest.getUsername());
            expectedProfile.setBirthday(updateRequest.getBirthday());
            expectedProfile.setHobbies(updateRequest.getHobbies());
            expectedProfile.setPurpose(updateRequest.getPurpose());
            expectedProfile.setPresentation(updateRequest.getPresentation());
            expectedProfile.setExpectation(updateRequest.getExpectation());
            expectedProfile.setContribution(updateRequest.getContribution());
            expectedProfile.setPhoneNumber(updateRequest.getPhoneNumber());
            given(profileService.updateProfile(any())).willReturn(expectedProfile);

            // when
            final ResultActions resultActions = mvc.perform(put(baseUri)
                    .with(csrf())
                    .content(toJson(updateRequest))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON));

            // then
            resultActionsAssertionsForSingleProfile(expectedProfile, resultActions);
            verify(profileService, times(1)).updateProfile(any());
        }

        @Test
        @WithMockUser
        @DisplayName("doit répondre BAD_REQUEST quand l'objet de requête est invalide")
        void should_response_bad_request_when_user_authenticated_but_body_is_not_valid() throws Exception {
            // given
            final ProfileUpdateRequest updateRequest = ProfileUpdateRequest.builder().email("mail-invalide").build();

            // when
            final ResultActions resultActions = mvc.perform(put(baseUri)
                    .with(csrf())
                    .content(toJson(updateRequest))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON));

            // then
            assertErrorResultFieldValidation(resultActions, Set.of("username", "email", "firstname", "lastname"));
            verifyNoInteractions(profileService);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_not_authenticated() throws Exception {
            // when
            final MockHttpServletRequestBuilder requestBuilder = put(baseUri)
                    .with(csrf())
                    .content(toJson(generateRandomProfileUpdateRequest()))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON);
            final ResultActions resultActions = mvc.perform(requestBuilder);

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(profileService);
        }
    }

    @Nested
    @DisplayName("# postProfileAvatar")
    class PostProfileAvatarTest {
        @Test
        @WithMockUser
        @DisplayName("doit répondre OK avec une imageUrl quand l'utilisateur est identifié")
        void should_response_ok_with_not_empty_image_url() throws Exception {
            // given
            final MockMultipartFile mockedImage = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
            final String username = "username";
            final ProfileDto expectedDto = ProfileDto.builder().imageUrl("http://url_du_fichier.jpeg").build();
            given(profileService.uploadAvatar(mockedImage, username)).willReturn(expectedDto);

            // when
            final MockHttpServletRequestBuilder requestBuilder = multipart(getFullPath(POST_PROFILE_AVATAR), username)
                    .file(mockedImage)
                    .with(csrf())
                    .content(toJson(generateRandomProfileUpdateRequest()))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON);
            final ResultActions resultActions = mvc.perform(requestBuilder);

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.imageUrl", is(notNullValue())));
            verify(profileService, times(1)).uploadAvatar(mockedImage, username);
        }

        @Test
        @DisplayName("doit répondre FORBIDDEN avec une imageUrl quand l'utilisateur n'est pas identifié")
        void should_response_forbidden_when_user_is_not_authenticated() throws Exception {
            // given
            final MockMultipartFile mockedImage = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
            final String username = "username";

            // when
            final MockHttpServletRequestBuilder requestBuilder = multipart(getFullPath(POST_PROFILE_AVATAR), username)
                    .file(mockedImage)
                    .with(csrf())
                    .content(toJson(generateRandomProfileUpdateRequest()))
                    .accept(APPLICATION_JSON)
                    .characterEncoding(Charset.defaultCharset())
                    .contentType(APPLICATION_JSON);
            final ResultActions resultActions = mvc.perform(requestBuilder);

            // then
            resultActions.andExpect(status().isForbidden());
            verifyNoInteractions(profileService);
        }
    }
}
