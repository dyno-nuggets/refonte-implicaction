package com.dynonuggets.refonteimplicaction.community.profile.utils;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.training.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperience;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomLocalDate;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
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

    public static ProfileModel generateRandomProfile() {
        return ProfileModel.builder()
                .id((long) generateRandomNumber())
                .user(generateRandomUser())
                .hobbies(randomAlphabetic(100))
                .purpose(randomAlphabetic(100))
                .presentation(randomAlphabetic(100))
                .expectation(randomAlphabetic(100))
                .contribution(randomAlphabetic(100))
                .phoneNumber(randomNumeric(8))
                .experiences(of(WorkExperience.builder().build()))
                .trainings(of(Training.builder().build()))
                .groups(of(GroupModel.builder().build()))
                .build();
    }

    public static ProfileDto generateRandomProfileDto() {
        return generateRandomProfileDto(randomAlphabetic(20));
    }

    public static ProfileDto generateRandomProfileDto(final String username) {
        final String firstname = randomAlphabetic(20);
        final String lastname = randomAlphabetic(20);
        return ProfileDto.builder()
                .username(username)
                .email(format("%s.%s@mail.com", firstname, lastname))
                .avatar(randomAlphabetic(20))
                .birthday(generateRandomLocalDate())
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
                .birthday(generateRandomLocalDate())
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
            final ProfileDto profileDto = pageElements.get(i);
            resultActionsAssertionsForSingleProfile(profileDto, resultActions, i);
        }
    }

    public static void resultActionsAssertionsForSingleProfile(final ProfileDto profileDto, final ResultActions resultActions) throws Exception {
        resultActionsAssertionsForSingleProfile(profileDto, resultActions, null);
    }

    public static void resultActionsAssertionsForSingleProfile(final ProfileDto profileDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        final String birthday = callIfNotNull(profileDto.getBirthday(), Object::toString);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.username", prefix), is(profileDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(profileDto.getEmail())))
                .andExpect(jsonPath(format("%s.avatar", prefix), is(profileDto.getAvatar())))
                .andExpect(jsonPath(format("%s.birthday", prefix), is(birthday)))
                .andExpect(jsonPath(format("%s.hobbies", prefix), is(profileDto.getHobbies())))
                .andExpect(jsonPath(format("%s.purpose", prefix), is(profileDto.getPurpose())))
                .andExpect(jsonPath(format("%s.presentation", prefix), is(profileDto.getPresentation())))
                .andExpect(jsonPath(format("%s.expectation", prefix), is(profileDto.getExpectation())))
                .andExpect(jsonPath(format("%s.contribution", prefix), is(profileDto.getContribution())))
                .andExpect(jsonPath(format("%s.phoneNumber", prefix), is(profileDto.getPhoneNumber())));
    }
}
