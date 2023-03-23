package com.dynonuggets.refonteimplicaction.user.utils;

import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@NoArgsConstructor(access = PRIVATE)
public class UserTestUtils {

    public static UserDto generateRandomUserDto() {
        return generateRandomUserDto(null, false);
    }

    public static UserDto generateRandomUserDto(final List<String> roles, final boolean isActive) {
        return UserDto.builder()
                .id((long) generateRandomNumber())
                .username(randomAlphabetic(10))
                .enabled(isActive)
                .roles(roles)
                .build();
    }

    public static void resultActionsValidationForPageUser(final Page<UserDto> userMockPage, final ResultActions resultActions) throws Exception {
        final List<UserDto> pageElements = userMockPage.getContent();

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(userMockPage.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(userMockPage.getTotalElements()));

        for (int i = 0; i < pageElements.size(); i++) {
            final UserDto userDto = pageElements.get(i);
            resultActionsValidationForSingleUser(userDto, resultActions, i);
        }
    }

    public static void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        final String registeredAt = callIfNotNull(userDto.getRegisteredAt(), Object::toString);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.id", prefix), is(userDto.getId().intValue())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(userDto.getEmail())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.registeredAt", prefix), is(registeredAt)));
    }
}
