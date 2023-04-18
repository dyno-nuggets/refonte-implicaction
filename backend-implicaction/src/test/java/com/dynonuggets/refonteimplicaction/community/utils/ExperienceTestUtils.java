package com.dynonuggets.refonteimplicaction.community.utils;

import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomLocalDate;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceTestUtils {

    public static WorkExperienceModel generateRandomExperience() {
        return WorkExperienceModel.builder()
                .id((long) generateRandomNumber())
                .profile(null)
                .startedAt(generateRandomLocalDate())
                .finishedAt(generateRandomLocalDate())
                .label(randomAlphabetic(20))
                .description(randomAlphabetic(120))
                .companyName(randomAlphabetic(15))
                .build();
    }

    public static WorkExperienceDto generateRandomExperienceDto() {
        return WorkExperienceDto.builder()
                .id((long) generateRandomNumber())
                .profile(null)
                .startedAt(generateRandomLocalDate())
                .finishedAt(generateRandomLocalDate())
                .label(randomAlphabetic(20))
                .description(randomAlphabetic(20))
                .companyName(randomAlphabetic(20))
                .build();
    }
}
