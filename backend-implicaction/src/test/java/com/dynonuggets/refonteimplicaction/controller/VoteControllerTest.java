package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.VoteDto;
import com.dynonuggets.refonteimplicaction.model.VoteType;
import com.dynonuggets.refonteimplicaction.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.VOTE_BASE_URI;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
class VoteControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    VoteService voteService;

    @Test
    @WithMockUser
    void should_vote() throws Exception {
        // given
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 123L);
        String json = gson.toJson(voteDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(VOTE_BASE_URI).content(json).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk());

        verify(voteService, times(1)).vote(any());
    }

    @Test
    void should_response_forbidden_when_no_authenticated() throws Exception {
        // given
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 123L);
        String json = gson.toJson(voteDto);

        // when
        final ResultActions resultActions = mvc.perform(post(VOTE_BASE_URI).content(json).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(voteService, never()).vote(any());
    }
}
