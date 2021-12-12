package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.service.WorkExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.DELETE_EXPERIENCES_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.EXPERIENCES_BASE_URI;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExperienceController.class)
class ExperienceControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    WorkExperienceService experienceService;
    ArrayList<String> roles = new ArrayList<>();
    UserDto user;

    @BeforeEach
    void setUp() {
        roles.add(RoleEnum.USER.getLongName());
        user = UserDto.builder()
                .id(3L)
                .username("paul-sdv")
                .firstname("Paul")
                .lastname("Flu")
                .email("paul@implicaction.fr")
                .url("www.google.fr")
                .hobbies("surf,gaming,judo")
                .purpose("")
                .registeredAt(null)
                .activatedAt(null)
                .roles(roles)
                .active(true)
                .build();
    }

    @Test
    @WithMockUser
    void should_create_experience_when_authenticated() throws Exception {
        // given
        WorkExperienceDto experienceDto = WorkExperienceDto.builder()
                .user(user)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(null)
                .finishedAt(null)
                .label("Label")
                .build();

        String json = gson.toJson(experienceDto);

        WorkExperienceDto expectedDto = WorkExperienceDto.builder()
                .id(123L)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(null)
                .finishedAt(null)
                .label("Label")
                .build();


        given(experienceService.saveOrUpdateExperience(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(EXPERIENCES_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.description", is(expectedDto.getDescription())))
                .andExpect(jsonPath("$.companyName", is(expectedDto.getCompanyName())))
                .andExpect(jsonPath("$.startedAt", is(expectedDto.getStartedAt())))
                .andExpect(jsonPath("$.finishedAt", is(expectedDto.getFinishedAt())))
                .andExpect(jsonPath("$.label", is(expectedDto.getLabel())));

        verify(experienceService, times(1)).saveOrUpdateExperience(any());
    }

    @Test
    void should_not_create_experience_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        WorkExperienceDto experienceDto = WorkExperienceDto.builder()
                .id(123L)
                .user(user)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .label("Label")
                .build();

        String json = gson.toJson(experienceDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(EXPERIENCES_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(experienceService, never()).saveOrUpdateExperience(any());
    }

    @Test
    @WithMockUser
    void should_update_experience_when_authenticated() throws Exception {
        // given
        WorkExperienceDto experienceDto = WorkExperienceDto.builder()
                .user(user)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(null)
                .finishedAt(null)
                .label("Label")
                .build();

        String json = gson.toJson(experienceDto);

        WorkExperienceDto expectedDto = WorkExperienceDto.builder()
                .id(123L)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(null)
                .finishedAt(null)
                .label("Label")
                .build();


        given(experienceService.saveOrUpdateExperience(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                put(EXPERIENCES_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.description", is(expectedDto.getDescription())))
                .andExpect(jsonPath("$.companyName", is(expectedDto.getCompanyName())))
                .andExpect(jsonPath("$.startedAt", is(expectedDto.getStartedAt())))
                .andExpect(jsonPath("$.finishedAt", is(expectedDto.getFinishedAt())))
                .andExpect(jsonPath("$.label", is(expectedDto.getLabel())));

        verify(experienceService, times(1)).saveOrUpdateExperience(any());
    }

    @Test
    void should_not_update_experience_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        WorkExperienceDto experienceDto = WorkExperienceDto.builder()
                .id(123L)
                .user(user)
                .companyName("Idemia")
                .description("Creation")
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .label("Label")
                .build();

        String json = gson.toJson(experienceDto);


        // when
        final ResultActions resultActions = mvc.perform(
                put(EXPERIENCES_BASE_URI).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(experienceService, never()).saveOrUpdateExperience(any());

    }

    @Test
    @WithMockUser
    void should_delete_experience_when_authenticated() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(
                delete(EXPERIENCES_BASE_URI + DELETE_EXPERIENCES_URI, 123L).with(csrf())
        );

        // then
        resultActions.andExpect(status().isNoContent());
        verify(experienceService, times(1)).deleteExperience(anyLong());
    }

    @Test
    void should_not_delete_experience_when_not_authenticated_and_should_return_forbidden() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(
                delete(EXPERIENCES_BASE_URI + DELETE_EXPERIENCES_URI, 123L)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(experienceService, never()).deleteExperience(anyLong());
    }

}
