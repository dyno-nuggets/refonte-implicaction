package com.dynonuggets.refonteimplicaction.community.utils;

import com.dynonuggets.refonteimplicaction.community.training.domain.model.TrainingModel;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomLocalDate;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingTestUtils {

    public static TrainingModel generateRandomTraining() {
        return TrainingModel.builder()
                .id((long) generateRandomNumber())
                .profile(null)
                .label(randomAlphabetic(20))
                .date(generateRandomLocalDate())
                .school(randomAlphabetic(20))
                .build();
    }

    public static TrainingDto generateRandomTrainingDto() {
        return TrainingDto.builder()
                .id((long) generateRandomNumber())
                .label(randomAlphabetic(20))
                .date(generateRandomLocalDate())
                .school(randomAlphabetic(20))
                .build();
    }
}
