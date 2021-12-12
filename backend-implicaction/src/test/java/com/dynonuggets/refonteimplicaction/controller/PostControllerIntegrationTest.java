package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.dto.PostRequest;
import com.dynonuggets.refonteimplicaction.dto.PostResponse;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.service.CommentService;
import com.dynonuggets.refonteimplicaction.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;
import static com.dynonuggets.refonteimplicaction.utils.Message.POST_NOT_FOUND_MESSAGE;
import static com.dynonuggets.refonteimplicaction.utils.Message.POST_SHOULD_HAVE_A_NAME;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerIntegrationTest extends ControllerIntegrationTestBase {

    @MockBean
    PostService postService;

    @MockBean
    CommentService commentService;

    @Test
    @WithMockUser
    void should_create_post_when_authenticated() throws Exception {
        // given
        PostRequest postRequest = PostRequest.builder()
                .groupId(125L)
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
                .groupName("divers")
                .voteCount(0)
                .commentCount(0)
                .duration("à l'instant")
                .upVote(false)
                .downVote(false)
                .voteCount(0)
                .commentCount(0)
                .build();
        given(postService.saveOrUpdate(any(PostRequest.class))).willReturn(expectedResponse);

        // when
        final ResultActions resultActions = mvc.perform(
                post(POSTS_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(expectedResponse.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedResponse.getName())))
                .andExpect(jsonPath("$.url", is(expectedResponse.getUrl())))
                .andExpect(jsonPath("$.description", is(expectedResponse.getDescription())))
                .andExpect(jsonPath("$.username", is(expectedResponse.getUsername())))
                .andExpect(jsonPath("$.groupName", is(expectedResponse.getGroupName())))
                .andExpect(jsonPath("$.voteCount", is(expectedResponse.getVoteCount())))
                .andExpect(jsonPath("$.commentCount", is(expectedResponse.getCommentCount())))
                .andExpect(jsonPath("$.duration", is(expectedResponse.getDuration())))
                .andExpect(jsonPath("$.upVote", is(expectedResponse.isUpVote())))
                .andExpect(jsonPath("$.downVote", is(expectedResponse.isDownVote())));

        verify(postService, times(1)).saveOrUpdate(any());
    }

    @Test
    @WithMockUser
    void should_response_bad_request_when_postname_is_null() throws Exception {
        // given
        PostRequest postRequest = PostRequest.builder()
                .groupId(125L)
                .name("")
                .url("http://url.com")
                .description("Il est super cool ce post")
                .build();

        String json = gson.toJson(postRequest);

        given(postService.saveOrUpdate(any(PostRequest.class))).willThrow(new IllegalArgumentException(POST_SHOULD_HAVE_A_NAME));

        // when
        final ResultActions resultActions = mvc.perform(
                post(POSTS_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", is(POST_SHOULD_HAVE_A_NAME)))
                .andExpect(jsonPath("$.errorCode", is(BAD_REQUEST.value())));
    }

    @Test
    void should_response_forbidden_when_not_authenticated() throws Exception {
        // given
        PostRequest postRequest = PostRequest.builder()
                .groupId(125L)
                .name("coucou post")
                .url("http://url.com")
                .description("Il est super cool ce post")
                .build();

        String json = gson.toJson(postRequest);

        // when
        final ResultActions resultActions = mvc.perform(
                post(JOBS_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(postService, never()).saveOrUpdate(any());
    }

    @Test
    @WithMockUser
    void should_get_post_when_authenticated_and_post_exists() throws Exception {
        // given
        PostResponse expectedResponse = PostResponse.builder()
                .id(123L)
                .username("toto")
                .description("Wahou awesome")
                .name("pretty cool post")
                .build();

        given(postService.getPost(anyLong())).willReturn(expectedResponse);

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_URI, expectedResponse.getId()).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(expectedResponse.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedResponse.getName())))
                .andExpect(jsonPath("$.url", is(expectedResponse.getUrl())))
                .andExpect(jsonPath("$.description", is(expectedResponse.getDescription())))
                .andExpect(jsonPath("$.username", is(expectedResponse.getUsername())))
                .andExpect(jsonPath("$.groupName", is(expectedResponse.getGroupName())))
                .andExpect(jsonPath("$.voteCount", is(expectedResponse.getVoteCount())))
                .andExpect(jsonPath("$.commentCount", is(expectedResponse.getCommentCount())))
                .andExpect(jsonPath("$.duration", is(expectedResponse.getDuration())))
                .andExpect(jsonPath("$.upVote", is(expectedResponse.isUpVote())))
                .andExpect(jsonPath("$.downVote", is(expectedResponse.isDownVote())));

        verify(postService, times(1)).getPost(anyLong());
    }

    @Test
    @WithMockUser
    void should_response_notfound_when_getting_post_and_authenticated_and_not_exists() throws Exception {
        // given
        long postId = 123L;
        given(postService.getPost(anyLong())).willThrow(new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_URI, postId).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isNotFound());

        verify(postService, times(1)).getPost(anyLong());
    }

    @Test
    void should_response_forbidden_when_getting_post_and_not_authenticated() throws Exception {
        // given
        long postId = 123L;

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_URI, postId).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(postService, never()).getPost(any());
    }

    @Test
    @WithMockUser
    void should_list_all_post_when_authenticated() throws Exception {
        // given
        PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://url.site", "Description", "User 1", 12L, null,
                "Subreddit Name", 0, 0, "il y a 2 jours", false, false, null);
        PostResponse postRequest2 = new PostResponse(2L, "Post Name 2", "http://url2.site2", "Description2", "User 2", 13L, null,
                "Subreddit Name 2", 0, 0, "il y a 2 jours", false, false, null);

        final List<PostResponse> postResponses = asList(postRequest1, postRequest2);
        final Page<PostResponse> expectedPages = new PageImpl<>(postResponses);
        final long expectedSize = expectedPages.getTotalElements();

        given(postService.getAllPosts(DEFAULT_PAGEABLE)).willReturn(expectedPages);

        // when
        final ResultActions resultActions = mvc.perform(get(POSTS_BASE_URI));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is((int) expectedSize)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.totalPages", is(1)));

        for (int i = 0; i < expectedSize; i++) {
            String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(postResponses.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".name", is(postResponses.get(i).getName())))
                    .andExpect(jsonPath(contentPath + ".url", is(postResponses.get(i).getUrl())));
        }

        verify(postService, times(1)).getAllPosts(any());
    }

    @Test
    @WithMockUser
    void should_return_page_of_comments_when_post_exists_and_authenticated() throws Exception {
        // given
        long postId = 123L;
        final List<CommentDto> commentDtos = asList(
                CommentDto.builder().id(1L).postId(postId).build(),
                CommentDto.builder().id(2L).postId(postId).build()
        );
        final Page<CommentDto> expectedPages = new PageImpl<>(commentDtos);
        final long expectedSize = expectedPages.getTotalElements();
        given(commentService.getAllCommentsForPost(any(), anyLong())).willReturn(expectedPages);

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_COMMENTS_URI, postId).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.numberOfElements", is((int) expectedSize)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.totalPages", is(1)));

        for (int i = 0; i < expectedSize; i++) {
            String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(commentDtos.get(i).getId().intValue())))
                    .andExpect(jsonPath(contentPath + ".postId", is(commentDtos.get(i).getPostId().intValue())));
        }

        verify(commentService, times(1)).getAllCommentsForPost(any(), anyLong());
    }

    @Test
    @WithMockUser
    void should_return_notfound_when_getting_comments_and_post_not_exists_and_authenticated() throws Exception {
        // given
        long postId = 123L;
        given(commentService.getAllCommentsForPost(any(), anyLong())).willThrow(new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_COMMENTS_URI, postId).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isNotFound());

        verify(commentService, times(1)).getAllCommentsForPost(any(), anyLong());
    }

    @Test
    void should_return_forbidden_when_getting_comments_and_not_authenticated() throws Exception {
        // given
        long postId = 123L;

        // when
        final ResultActions resultActions = mvc.perform(
                get(POSTS_BASE_URI + GET_POST_COMMENTS_URI, postId).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(commentService, never()).getAllCommentsForPost(any(), anyLong());
    }
}
