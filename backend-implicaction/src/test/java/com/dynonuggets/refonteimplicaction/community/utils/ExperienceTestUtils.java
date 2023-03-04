package com.dynonuggets.refonteimplicaction.community.utils;

import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.rest.dto.WorkExperienceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.util.TestUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceTestUtils {

    public static WorkExperience generateRandomExperience() {
        return WorkExperience.builder()
                .id((long) generateRandomNumber())
                .profile(null)
                .startedAt(toLocalDate(generateRandomDate()))
                .finishedAt(toLocalDate(generateRandomDate()))
                .label(randomAlphabetic(20))
                .description(randomAlphabetic(120))
                .companyName(randomAlphabetic(15))
                .build();
    }

    public static WorkExperienceDto generateRandomExperienceDto() {
        return WorkExperienceDto.builder()
                .id((long) generateRandomNumber())
                .profile(null)
                .startedAt(toLocalDate(generateRandomDate()))
                .finishedAt(toLocalDate(generateRandomDate()))
                .label(randomAlphabetic(20))
                .description(randomAlphabetic(20))
                .companyName(randomAlphabetic(20))
                .build();
    }
}
