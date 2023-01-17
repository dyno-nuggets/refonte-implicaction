package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.utils.TestUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceUtils {

    public static WorkExperience generateRandomExperience() {
        return WorkExperience.builder()
                .id((long) generateRandomNumber())
                .user(null)
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
                .user(null)
                .startedAt(toLocalDate(generateRandomDate()))
                .finishedAt(toLocalDate(generateRandomDate()))
                .label(randomAlphabetic(20))
                .description(randomAlphabetic(20))
                .companyName(randomAlphabetic(20))
                .build();
    }
}
