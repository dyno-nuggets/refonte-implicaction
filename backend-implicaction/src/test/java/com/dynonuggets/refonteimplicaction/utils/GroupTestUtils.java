package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.utils.TestUtils.*;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor(access = PRIVATE)
public class GroupTestUtils {

    public static GroupDto generateRandomGroupDto() {
        return GroupDto.builder()
                .id((long) generateRandomNumber())
                .name(randomAlphabetic(20))
                .description(randomAlphabetic(120))
                .numberOfPosts(generateRandomNumber())
                .imageUrl(randomAlphabetic(20))
                .createdAt(generateRandomInstant(Instant.parse("1950-12-03T10:15:30.00Z")))
                .valid(generateRandomBoolean())
                .numberOfUsers(generateRandomNumber())
                .username(randomAlphabetic(20))
                .userId((long) generateRandomNumber())
                .build();
    }
}
