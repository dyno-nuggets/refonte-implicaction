package com.dynonuggets.refonteimplicaction.community.training.controller;

import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.training.service.TrainingService;
import com.dynonuggets.refonteimplicaction.core.controller.ControllerIntegrationTestBase;
import com.dynonuggets.refonteimplicaction.core.domain.model.properties.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.core.dto.UserDto;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static com.dynonuggets.refonteimplicaction.community.training.utils.TrainingUris.DELETE_TRAINING_URI;
import static com.dynonuggets.refonteimplicaction.community.training.utils.TrainingUris.TRAINING_BASE_URI;
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

    @Getter
    protected String baseUri = TRAINING_BASE_URI;

    @MockBean
    TrainingService trainingService;
    Set<RoleEnum> roles = new HashSet<>();
    UserDto user;

    @BeforeEach
    void setUp() {
        roles.add(RoleEnum.ROLE_USER);
        user = UserDto.builder()
                .id(3L)
                .username("paul-sdv")
                .email("paul@implicaction.fr")
                .registeredAt(null)
                .roles(roles)
                .enabled(true)
                .build();
    }

    @Test
    @WithMockUser
    void should_create_training_when_authenticated() throws Exception {
        // given
        final TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        final String json = gson.toJson(trainingDto);

        final TrainingDto expectedDto = TrainingDto.builder()
                .id(123L)
                .school("SDV")
                .date(null)
                .label("Label")
                .build();


        given(trainingService.saveOrUpdateTraining(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

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
        final TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        final String json = gson.toJson(trainingDto);

        // when
        final ResultActions resultActions = mvc.perform(post(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).saveOrUpdateTraining(any());
    }

    @Test
    @WithMockUser
    void should_update_training_when_authenticated() throws Exception {
// given
        final TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        final String json = gson.toJson(trainingDto);

        final TrainingDto expectedDto = TrainingDto.builder()
                .id(123L)
                .school("SDV")
                .date(null)
                .label("Label")
                .build();


        given(trainingService.saveOrUpdateTraining(any())).willReturn(expectedDto);

        // when
        final ResultActions resultActions = mvc.perform(put(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

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
        final TrainingDto trainingDto = TrainingDto.builder()
                .school("SDV")
                .date(null)
                .label("Label")
                .build();

        final String json = gson.toJson(trainingDto);

        // when
        final ResultActions resultActions = mvc.perform(put(baseUri)
                .content(json)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).saveOrUpdateTraining(any());
    }

    @Test
    @WithMockUser
    void should_delete_experience_when_authenticated() throws Exception {

        // when
        final ResultActions resultActions = mvc.perform(delete(getFullPath(DELETE_TRAINING_URI), 123L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .with(csrf()));

        // then
        resultActions.andExpect(status().isNoContent());
        verify(trainingService, times(1)).deleteTraining(anyLong());
    }

    @Test
    void should_not_delete_experience_when_not_authenticated_and_should_return_forbidden() throws Exception {
        // when
        final ResultActions resultActions = mvc.perform(delete(getFullPath(DELETE_TRAINING_URI), 123L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON));

        // then
        resultActions.andDo(print()).andExpect(status().isForbidden());
        verify(trainingService, never()).deleteTraining(anyLong());
    }
}

