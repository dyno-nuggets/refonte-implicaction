package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.model.Training;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.dynonuggets.refonteimplicaction.core.util.TestUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingUtils {

    public static Training generateRandomTraining() {
        return Training.builder()
                .id((long) generateRandomNumber())
                .user(null)
                .label(randomAlphabetic(20))
                .date(toLocalDate(generateRandomDate()))
                .school(randomAlphabetic(20))
                .build();
    }

    public static TrainingDto generateRandomTrainingDto() {
        return TrainingDto.builder()
                .id((long) generateRandomNumber())
                .label(randomAlphabetic(20))
                .date(toLocalDate(generateRandomDate()))
                .school(randomAlphabetic(20))
                .build();
    }
}
