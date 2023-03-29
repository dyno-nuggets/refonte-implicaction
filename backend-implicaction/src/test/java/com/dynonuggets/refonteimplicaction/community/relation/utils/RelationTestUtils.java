package com.dynonuggets.refonteimplicaction.community.relation.utils;

import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileTestUtils.generateRandomProfileDto;
import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomInstant;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.generateRandomNumber;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RelationTestUtils {
    public static RelationsDto generateRandomRelationDto(final boolean isConfirmed, final String sender, final String receiver) {
        final Instant sentAt = generateRandomInstant();
        final Instant confirmedAt = isConfirmed ? generateRandomInstant(sentAt) : null;
        return RelationsDto.builder()
                .id((long) generateRandomNumber())
                .sender(sender != null ? generateRandomProfileDto(sender) : null)
                .receiver(receiver != null ? generateRandomProfileDto(receiver) : null)
                .sentAt(sentAt)
                .confirmedAt(confirmedAt)
                .build();
    }

    public static void resultActionsAssertionForSingleRelation(final ResultActions resultActions, final RelationsDto relationsDto) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        resultActionsAssertionForSingleRelation(resultActions, relationsDto, null);
    }

    public static void resultActionsAssertionsForRelations(final ResultActions resultActions, final PageImpl<RelationsDto> expected) throws Exception {
        final List<RelationsDto> relationsDtos = expected.getContent();
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(expected.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));

        for (int i = 0; i < relationsDtos.size(); i++) {
            resultActionsAssertionForSingleRelation(resultActions, relationsDtos.get(i), i);
        }
    }

    private static void resultActionsAssertionForSingleRelation(final ResultActions resultActions, final RelationsDto relationsDto, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        resultActions.andExpect(jsonPath(format("%s.id", prefix), is(relationsDto.getId().intValue())))
                .andExpect(jsonPath(format("%s.sentAt", prefix), is(callIfNotNull(relationsDto.getSentAt(), Instant::toString))))
                .andExpect(jsonPath(format("%s.confirmedAt", prefix), is(callIfNotNull(relationsDto.getConfirmedAt(), Instant::toString))))
                .andExpect(jsonPath(format("%s.receiver.username", prefix), is(relationsDto.getReceiver().getUsername())));

        // contrairement au receiver, le sender d’une relation peut être nul dans le cas de l’envoie de toute la communauté
        if (relationsDto.getSender() != null) {
            resultActions.andExpect(jsonPath(format("%s.sender.username", prefix), is(relationsDto.getSender().getUsername())));
        } else {
            resultActions.andExpect(jsonPath(format("%s.sender", prefix), is(nullValue())));
        }
    }
}
