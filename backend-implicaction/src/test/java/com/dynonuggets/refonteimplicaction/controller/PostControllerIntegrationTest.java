package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.PostService;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.JOB_BASE_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.POST_BASE_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerIntegrationTest {

    Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    void should_create_post_when_authenticated() throws Exception {
        // given
        PostRequest postRequest = PostRequest.builder()
                .subredditName("divers")
                .name("coucou post")
                .url("http://url.com")
                .description("Il est super cool ce post")
                .build();

        String json = gson.toJson(postRequest);

        PostResponse expectedResponse = PostResponse.builder()
                .id(123L)
                .name("coucou post")
                .url("http://url.com")
                .description("Il est super cool ce post")
                .username("Matthieu")
                .subredditName("divers")
                .voteCount(0)
                .commentCount(0)
                .duration("à l'instant")
                .upVote(false)
                .downVote(false)
                .voteCount(0)
                .commentCount(0)
                .build();
        given(postService.save(any(PostRequest.class))).willReturn(expectedResponse);

        // when
        final ResultActions resultActions = mvc.perform(
                post(POST_BASE_URI).content(json).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(Math.toIntExact(expectedResponse.getId()))))
                .andExpect(jsonPath("$.name", is(expectedResponse.getName())))
                .andExpect(jsonPath("$.url", is(expectedResponse.getUrl())))
                .andExpect(jsonPath("$.description", is(expectedResponse.getDescription())))
                .andExpect(jsonPath("$.username", is(expectedResponse.getUsername())))
                .andExpect(jsonPath("$.subredditName", is(expectedResponse.getSubredditName())))
                .andExpect(jsonPath("$.voteCount", is(expectedResponse.getVoteCount())))
                .andExpect(jsonPath("$.commentCount", is(expectedResponse.getCommentCount())))
                .andExpect(jsonPath("$.duration", is(expectedResponse.getDuration())))
                .andExpect(jsonPath("$.upVote", is(expectedResponse.isUpVote())))
                .andExpect(jsonPath("$.downVote", is(expectedResponse.isDownVote())));

        verify(postService, times(1)).save(any());
    }

    @Test
    void should_response_forbidden_when_not_authenticated() throws Exception {
        // given
        PostRequest postRequest = PostRequest.builder()
                .subredditName("divers")
                .name("coucou post")
                .url("http://url.com")
                .description("Il est super cool ce post")
                .build();

        String json = gson.toJson(postRequest);

        // when
        final ResultActions resultActions = mvc.perform(
                post(JOB_BASE_URI).content(json).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(postService, never()).save(any());
    }
}
