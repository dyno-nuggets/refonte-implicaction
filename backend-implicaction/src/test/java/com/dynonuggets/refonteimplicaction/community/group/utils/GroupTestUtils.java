package com.dynonuggets.refonteimplicaction.community.group.utils;

import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.generateRandomProfile;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomInstant;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = PRIVATE)
public class GroupTestUtils {

    public static GroupModel generateRandomGroup(final boolean isEnable) {
        return GroupModel.builder()
                .id((long) generateRandomNumber())
                .name(randomAlphabetic(20))
                .description(randomAlphabetic(120))
                .imageUrl(randomAlphabetic(20))
                .createdAt(generateRandomInstant(Instant.parse("1950-12-03T10:15:30.00Z")))
                .enabled(isEnable)
                .creator(generateRandomProfile())
                .build();
    }
}
