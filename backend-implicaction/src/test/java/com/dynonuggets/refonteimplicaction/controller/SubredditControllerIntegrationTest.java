package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.service.SubredditService;
import com.google.common.collect.Ordering;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SubredditController.class)
class SubredditControllerIntegrationTest extends ControllerIntegrationTestBase {

    @MockBean
    SubredditService subredditService;

    @Test
    void should_response_forbidden_when_create_subreddit_with_no_authentication() throws Exception {
        // given
        final SubredditDto sentDto = SubredditDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();
        String json = gson.toJson(sentDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(JOBS_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(subredditService, never()).save(any());
    }

    @Test
    @WithMockUser
    void should_list_all_subreddit_whith_no_authentication() throws Exception {
        // given
        final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
        final Page<SubredditDto> subreddits = new PageImpl<>(asList(
                SubredditDto.builder().id(1L).build(),
                SubredditDto.builder().id(2L).build(),
                SubredditDto.builder().id(3L).build(),
                SubredditDto.builder().id(4L).build(),
                SubredditDto.builder().id(5L).build(),
                SubredditDto.builder().id(10L).build(),
                SubredditDto.builder().id(12L).build(),
                SubredditDto.builder().id(13L).build(),
                SubredditDto.builder().id(14L).build(),
                SubredditDto.builder().id(15L).build()
        ));
        given(subredditService.getAll(DEFAULT_PAGEABLE)).willReturn(subreddits);

        // when
        final ResultActions resultActions = mvc.perform(get(SUBREDDITS_BASE_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(subreddits.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(subreddits.getTotalElements()));

        final List<SubredditDto> subredditDtos = subreddits.get().collect(toList());
        final int size = subredditDtos.size();

        for (int i = 0; i < size; i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(subredditDtos.get(i).getId()))));
        }
        resultActions.andReturn();

        verify(subredditService, times(1)).getAll(any());
    }

    @Test
    void should_response_forbidden_when_listing_all_subreddit_whith_no_authentication() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(JOBS_BASE_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(subredditService, never()).getAll(any());
    }

    @Test
    @WithMockUser
    void should_get_all_by_top_posting_when_authenticated() throws Exception {
        // given
        int limit = 5;
        List<SubredditDto> expectedDtos = asList(
                SubredditDto.builder().id(1L).numberOfPosts(5).build(),
                SubredditDto.builder().id(1L).numberOfPosts(4).build(),
                SubredditDto.builder().id(1L).numberOfPosts(3).build(),
                SubredditDto.builder().id(1L).numberOfPosts(2).build(),
                SubredditDto.builder().id(1L).numberOfPosts(1).build()
        );

        given(subredditService.getAllByTopPosting(anyInt())).willReturn(expectedDtos);

        // when
        final ResultActions resultActions = mvc.perform(
                get(SUBREDDITS_BASE_URI + GET_ALL_BY_TOP_POSTING_URI).param("limit", String.valueOf(limit))
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(limit));

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        final List<SubredditDto> actualDtos = gson.fromJson(contentAsString, new TypeToken<List<SubredditDto>>() {
        }.getType());

        final List<Integer> collect = actualDtos.stream().map(SubredditDto::getNumberOfPosts).collect(toList());
        assertTrue(Ordering.natural().reverse().isOrdered(collect));

        verify(subredditService, times(1)).getAllByTopPosting(anyInt());
    }


    @Test
    void should_response_forbidden_when_top_posting_and_not_authenticated() throws Exception {
        // given
        int limit = 5;

        // when
        final ResultActions resultActions = mvc.perform(
                get(SUBREDDITS_BASE_URI + GET_ALL_BY_TOP_POSTING_URI).param("limit", String.valueOf(limit))
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(subredditService, never()).getAllByTopPosting(anyInt());
    }
}
