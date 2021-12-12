package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.DELETE_TRAINING_URI;
import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.TRAINING_BASE_URI;
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

@WebMvcTest(controllers = TrainingController.class)
class TrainingControllerTest extends ControllerIntegrationTestBase {

    @MockBean
    TrainingService trainingService;
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
    void should_create_training_when_authenticated() throws Exception {
        // given
        TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        String json = gson.toJson(trainingDto);

        TrainingDto expectedDto = TrainingDto.builder()
                .id(123L)
                .school("SDV")
                .date(null)
                .label("Label")
                .build();


        given(trainingService.saveOrUpdateTraining(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(TRAINING_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.date", is(expectedDto.getDate())))
                .andExpect(jsonPath("$.school", is(expectedDto.getSchool())))
                .andExpect(jsonPath("$.label", is(expectedDto.getLabel())));

        verify(trainingService, times(1)).saveOrUpdateTraining(any());
    }

    @Test
    void should_not_create_training_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        String json = gson.toJson(trainingDto);

        // when
        final ResultActions resultActions = mvc.perform(
                post(TRAINING_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).saveOrUpdateTraining(any());
    }

    @Test
    @WithMockUser
    void should_update_training_when_authenticated() throws Exception {
// given
        TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        String json = gson.toJson(trainingDto);

        TrainingDto expectedDto = TrainingDto.builder()
                .id(123L)
                .school("SDV")
                .date(null)
                .label("Label")
                .build();


        given(trainingService.saveOrUpdateTraining(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(
                put(TRAINING_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).with(csrf())
        );

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(expectedDto.getId().intValue())))
                .andExpect(jsonPath("$.date", is(expectedDto.getDate())))
                .andExpect(jsonPath("$.school", is(expectedDto.getSchool())))
                .andExpect(jsonPath("$.label", is(expectedDto.getLabel())));

        verify(trainingService, times(1)).saveOrUpdateTraining(any());
    }

    @Test
    void should_not_update_training_and_response_forbidden_when_not_authenticated() throws Exception {
        // given
        TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        String json = gson.toJson(trainingDto);

        // when
        final ResultActions resultActions = mvc.perform(
                put(TRAINING_BASE_URI).content(json).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).saveOrUpdateTraining(any());
    }

    @Test
    @WithMockUser
    void should_delete_experience_when_authenticated() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(
                delete(TRAINING_BASE_URI + DELETE_TRAINING_URI, 123L).with(csrf())
        );

        // then
        resultActions.andExpect(status().isNoContent());
        verify(trainingService, times(1)).deleteTraining(anyLong());
    }

    @Test
    void should_not_delete_experience_when_not_authenticated_and_should_return_forbidden() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(
                delete(TRAINING_BASE_URI + DELETE_TRAINING_URI, 123L)
        );

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).deleteTraining(anyLong());
    }
}

