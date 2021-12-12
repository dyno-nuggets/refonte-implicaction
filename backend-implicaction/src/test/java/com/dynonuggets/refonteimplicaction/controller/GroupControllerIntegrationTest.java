package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.service.GroupService;
import com.google.common.collect.Ordering;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

@WebMvcTest(controllers = GroupController.class)
class GroupControllerIntegrationTest extends ControllerIntegrationTestBase {

    @MockBean
    GroupService groupService;

    @Mock
    UserRepository userRepository;

    @Test
    void should_response_forbidden_when_create_subreddit_with_no_authentication() throws Exception {
        // given
        final GroupDto sentDto = GroupDto.builder()
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

        verify(groupService, never()).save(any());
    }

    @Test
    @WithMockUser
    void should_list_all_subreddit_with_no_authentication() throws Exception {
        // given
        final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "id");
        final Page<GroupDto> subreddits = new PageImpl<>(asList(
                GroupDto.builder().id(1L).build(),
                GroupDto.builder().id(2L).build(),
                GroupDto.builder().id(3L).build(),
                GroupDto.builder().id(4L).build(),
                GroupDto.builder().id(5L).build(),
                GroupDto.builder().id(10L).build(),
                GroupDto.builder().id(12L).build(),
                GroupDto.builder().id(13L).build(),
                GroupDto.builder().id(14L).build(),
                GroupDto.builder().id(15L).build()
        ));
        final User user = User.builder()
                .id(1L)
                .username("test")
                .build();

        given(groupService.getAllValidGroups(DEFAULT_PAGEABLE)).willReturn(subreddits);
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        // when
        final ResultActions resultActions = mvc.perform(get(GROUPS_BASE_URI + GET_VALIDATED_GROUPS_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(subreddits.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(subreddits.getTotalElements()));

        final List<GroupDto> groupDtos = subreddits.get().collect(toList());
        final int size = groupDtos.size();

        for (int i = 0; i < size; i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(groupDtos.get(i).getId()))));
        }
        resultActions.andReturn();

        verify(groupService, times(1)).getAllValidGroups(any());
    }

    @Test
    void should_response_forbidden_when_listing_all_subreddit_whith_no_authentication() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(get(JOBS_BASE_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(groupService, never()).getAllValidGroups(any());
    }

    @Test
    @WithMockUser
    void should_get_all_by_top_posting_when_authenticated() throws Exception {
        // given
        int limit = 5;
        List<GroupDto> expectedDtos = asList(
                GroupDto.builder().id(1L).numberOfPosts(5).build(),
                GroupDto.builder().id(1L).numberOfPosts(4).build(),
                GroupDto.builder().id(1L).numberOfPosts(3).build(),
                GroupDto.builder().id(1L).numberOfPosts(2).build(),
                GroupDto.builder().id(1L).numberOfPosts(1).build()
        );

        given(groupService.getAllByTopPosting(anyInt())).willReturn(expectedDtos);

        // when
        final ResultActions resultActions = mvc.perform(
                get(GROUPS_BASE_URI + GET_ALL_BY_TOP_POSTING_URI).param("limit", String.valueOf(limit))
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(limit));

        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        final List<GroupDto> actualDtos = gson.fromJson(contentAsString, new TypeToken<List<GroupDto>>() {
        }.getType());

        final List<Integer> collect = actualDtos.stream().map(GroupDto::getNumberOfPosts).collect(toList());
        assertTrue(Ordering.natural().reverse().isOrdered(collect));

        verify(groupService, times(1)).getAllByTopPosting(anyInt());
    }


    @Test
    void should_response_forbidden_when_top_posting_and_not_authenticated() throws Exception {
        // given
        int limit = 5;

        // when
        final ResultActions resultActions = mvc.perform(
                get(GROUPS_BASE_URI + GET_ALL_BY_TOP_POSTING_URI).param("limit", String.valueOf(limit))
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(groupService, never()).getAllByTopPosting(anyInt());
    }

    @Test
    @WithMockUser
    void should_get_all_pending_groups_when_authenticated() throws Exception {
        //given
        List<GroupDto> groupDtos = Arrays.asList(
                GroupDto.builder().id(1L).valid(false).build(),
                GroupDto.builder().id(2L).valid(false).build(),
                GroupDto.builder().id(3L).valid(false).build()
        );
        Page<GroupDto> groupPageMockResponse = new PageImpl<>(groupDtos);
        given(groupService.getAllPendingGroups(any())).willReturn(groupPageMockResponse);

        // when
        ResultActions resultActions = mvc.perform(
                get(GROUPS_BASE_URI + GET_PENDING_GROUP_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < groupDtos.size(); i++) {
            final String contentPath = String.format("$.content[%d]", i);
            resultActions.andExpect(jsonPath(contentPath + ".id", is(Math.toIntExact(groupDtos.get(i).getId()))));
            resultActions.andExpect(jsonPath(contentPath + ".valid", is(groupDtos.get(i).isValid())));
        }

        verify(groupService, times(1)).getAllPendingGroups(any());
    }

    @Test
    void should_response_forbidden_when_pending_groups_and_not_authenticated() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(get(GROUPS_BASE_URI + GET_PENDING_GROUP_URI).contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());

        verify(groupService, never()).getAllPendingGroups(any());
    }
}
