package com.dynonuggets.refonteimplicaction.community.utils;

import com.dynonuggets.refonteimplicaction.community.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileUpdateRequest;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.auth.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.core.util.TestUtils.*;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static java.lang.String.format;
import static java.util.List.of;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@NoArgsConstructor(access = PRIVATE)
public class ProfileTestUtils {

    public static Profile generateRandomProfile() {
        return Profile.builder()
                .id((long) generateRandomNumber())
                .user(generateRandomUser())
                .firstname(randomAlphabetic(20))
                .lastname(randomAlphabetic(20))
                .birthday(toLocalDate(generateRandomDate()))
                .hobbies(randomAlphabetic(100))
                .purpose(randomAlphabetic(100))
                .presentation(randomAlphabetic(100))
                .expectation(randomAlphabetic(100))
                .contribution(randomAlphabetic(100))
                .phoneNumber(randomNumeric(8))
                .experiences(of(WorkExperience.builder().build()))
                .trainings(of(Training.builder().build()))
                .groups(of(Group.builder().build()))
                .build();
    }

    public static ProfileDto generateRandomProfileDto() {
        final String firstname = randomAlphabetic(20);
        final String lastname = randomAlphabetic(20);
        return ProfileDto.builder()
                .username(randomAlphabetic(20))
                .email(format("%s.%s@mail.com", firstname, lastname))
                .avatar(randomAlphabetic(20))
                .firstname(firstname)
                .lastname(lastname)
                .birthday(toLocalDate(generateRandomDate()))
                .hobbies(randomAlphabetic(100))
                .purpose(randomAlphabetic(100))
                .presentation(randomAlphabetic(100))
                .expectation(randomAlphabetic(100))
                .contribution(randomAlphabetic(100))
                .phoneNumber(randomNumeric(8))
                //.experiences()
                //.trainings()
                //.groups()
                .build();
    }

    public static ProfileUpdateRequest generateRandomProfileUpdateRequest(final String username) {
        return ProfileUpdateRequest.builder()
                .username(username)
                .firstname(randomAlphabetic(20))
                .lastname(randomAlphabetic(20))
                .birthday(toLocalDate(generateRandomDate()))
                .hobbies(randomAlphabetic(120))
                .purpose(randomAlphabetic(120))
                .presentation(randomAlphabetic(120))
                .expectation(randomAlphabetic(120))
                .contribution(randomAlphabetic(120))
                .phoneNumber(randomAlphabetic(120))
                .build();
    }

    public static ProfileUpdateRequest generateRandomProfileUpdateRequest() {
        return generateRandomProfileUpdateRequest(randomAlphabetic(20));
    }

    public static void resultActionsValidationForPageProfile(final Page<ProfileDto> profileDtos, final ResultActions resultActions) throws Exception {
        final List<ProfileDto> pageElements = profileDtos.getContent();

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(profileDtos.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(profileDtos.getTotalElements()));

        for (int i = 0; i < pageElements.size(); i++) {
            final ProfileDto userDto = pageElements.get(i);
            resultActionsValidationForSingleProfile(userDto, resultActions, i);
        }
    }

    public static void resultActionsValidationForSingleProfile(final ProfileDto profileDto, final ResultActions resultActions) throws Exception {
        resultActionsValidationForSingleProfile(profileDto, resultActions, null);
    }

    public static void resultActionsValidationForSingleProfile(final ProfileDto profileDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        final String birthday = callIfNotNull(profileDto.getBirthday(), Object::toString);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.username", prefix), is(profileDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(profileDto.getEmail())))
                .andExpect(jsonPath(format("%s.avatar", prefix), is(profileDto.getAvatar())))
                .andExpect(jsonPath(format("%s.firstname", prefix), is(profileDto.getFirstname())))
                .andExpect(jsonPath(format("%s.lastname", prefix), is(profileDto.getLastname())))
                .andExpect(jsonPath(format("%s.birthday", prefix), is(birthday)))
                .andExpect(jsonPath(format("%s.hobbies", prefix), is(profileDto.getHobbies())))
                .andExpect(jsonPath(format("%s.purpose", prefix), is(profileDto.getPurpose())))
                .andExpect(jsonPath(format("%s.presentation", prefix), is(profileDto.getPresentation())))
                .andExpect(jsonPath(format("%s.expectation", prefix), is(profileDto.getExpectation())))
                .andExpect(jsonPath(format("%s.contribution", prefix), is(profileDto.getContribution())))
                .andExpect(jsonPath(format("%s.phoneNumber", prefix), is(profileDto.getPhoneNumber())));
    }
}
