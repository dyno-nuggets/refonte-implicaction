package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.util.DateUtils;
import com.dynonuggets.refonteimplicaction.dto.CommentDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.service.CommentService;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.COMMENTS_BASE_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.GET_COMMENT_URI;
import static com.dynonuggets.refonteimplicaction.core.util.Message.COMMENT_NOT_FOUND;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerIntegrationTest extends ControllerIntegrationTestBase {

    @Getter
    protected String baseUri = COMMENTS_BASE_URI;

    @MockBean
    CommentService commentService;

    @Test
    @WithMockUser
    void should_create_comment_when_authenticated() throws Exception {
        // given
        final CommentDto sentDto = CommentDto.builder()
                .postId(243L)
                .text("voici mon commentaire sur ce point !")
                .username("Marc Elbichon")
                .build();

        final String json = gson.toJson(sentDto);

        final CommentDto expectedDto = CommentDto.builder()
                .id(123L)
                .postId(243L)
                .duration(DateUtils.getDurationAsString(Instant.now()))
                .text("voici mon commentaire sur ce point !")
                .username("Marc Elbichon")
                .build();


        given(commentService.saveOrUpdate(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.postId", is(expectedDto.getPostId().intValue())))
                .andExpect(jsonPath("$.duration", is(DateUtils.getDurationAsString(Instant.now()))))
                .andExpect(jsonPath("$.text", is(expectedDto.getText())))
                .andExpect(jsonPath("$.username", is(expectedDto.getUsername())));
        verify(commentService, times(1)).saveOrUpdate(any());
    }

    @Test
    void should_not_create_comment_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        final CommentDto sentDto = CommentDto.builder()
                .postId(243L)
                .text("voici mon commentaire sur ce point !")
                .username("Marc Elbichon")
                .build();

        final String json = gson.toJson(sentDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void should_get_comment_when_exists_and_authenticated() throws Exception {
        // given
        final CommentDto expectedDto = CommentDto.builder()
                .id(123L)
                .postId(243L)
                .duration(DateUtils.getDurationAsString(Instant.now()))
                .text("voici mon commentaire sur ce point !")
                .username("Marc Elbichon")
                .build();
        given(commentService.getComment(anyLong())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_COMMENT_URI), expectedDto.getId())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.postId", is(expectedDto.getPostId().intValue())))
                .andExpect(jsonPath("$.duration", is(DateUtils.getDurationAsString(Instant.now()))))
                .andExpect(jsonPath("$.text", is(expectedDto.getText())))
                .andExpect(jsonPath("$.username", is(expectedDto.getUsername())));

        verify(commentService, times(1)).getComment(anyLong());
    }

    @Test
    @WithMockUser
    void should_response_not_found_when_authenticated_and_not_exists() throws Exception {
        // given
        final long commentId = 123L;
        given(commentService.getComment(anyLong())).willThrow(new NotFoundException(String.format(COMMENT_NOT_FOUND, commentId)));

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_COMMENT_URI), commentId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
        verify(commentService, times(1)).getComment(anyLong());
    }

    @Test
    void should_response_forbidden_when_getting_comment_and_not_authenticated() throws Exception {
        // given
        final long commentId = 123L;

        // when
        final ResultActions resultActions = mvc.perform(get(getFullPath(GET_COMMENT_URI), commentId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(commentService, never()).getComment(anyLong());
    }
}
