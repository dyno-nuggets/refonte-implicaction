package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.SubredditService;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.Group.BASE_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubredditController.class)
class SubredditControllerIntegrationTest {

    Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private SubredditService subredditService;

    @Test
    @WithMockUser
    void should_create_subreddit_when_authenticated() throws Exception {
        // given
        final SubredditDto sentDto = SubredditDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();
        String json = gson.toJson(sentDto);

        SubredditDto expected = SubredditDto.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        given(subredditService.save(any())).willReturn(expected);

        // when
        final ResultActions resultActions = mvc.perform(post(BASE_URI).content(json).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(Math.toIntExact(expected.getId()))))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
        verify(subredditService, times(1)).save(any());
    }

    @Test
    void should_response_forbidden_when_create_subreddit_whith_no_authentication() throws Exception {
        // given
        final SubredditDto sentDto = SubredditDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();
        String json = gson.toJson(sentDto);

        // when
        final ResultActions resultActions = mvc.perform(post(BASE_URI).content(json).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isForbidden());
        verify(subredditService, never()).save(any());
    }

    @Test
    @WithMockUser
    void should_list_all_subreddit_whith_no_authentication() throws Exception {
        // given
        final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
        final Page<SubredditDto> subreddits = new PageImpl<>(Arrays.asList(
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
        final ResultActions resultActions = mvc.perform(get(BASE_URI).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(subreddits.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(subreddits.getTotalElements()));

        final List<SubredditDto> subredditDtos = subreddits.get().collect(Collectors.toList());
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
        final ResultActions resultActions = mvc.perform(get(BASE_URI).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isForbidden());
        verify(subredditService, never()).getAll(any());
    }
}
