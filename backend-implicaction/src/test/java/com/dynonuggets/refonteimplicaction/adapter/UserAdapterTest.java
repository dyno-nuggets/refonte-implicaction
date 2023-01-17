package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.Role;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.service.FileService;
import com.dynonuggets.refonteimplicaction.utils.UserUtils;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dynonuggets.refonteimplicaction.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.utils.ExperienceUtils.generateRandomExperience;
import static com.dynonuggets.refonteimplicaction.utils.ExperienceUtils.generateRandomExperienceDto;
import static com.dynonuggets.refonteimplicaction.utils.TrainingUtils.generateRandomTraining;
import static com.dynonuggets.refonteimplicaction.utils.TrainingUtils.generateRandomTrainingDto;
import static com.dynonuggets.refonteimplicaction.utils.UserUtils.generateRandomUser;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserAdapterTest {

    private static final RecursiveComparisonConfiguration TO_DTO_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withIgnoredFields("imageUrl", "roles", "experiences", "trainings", "relationTypeOfCurrentUser")
                    .build();
    private static final RecursiveComparisonConfiguration TO_DTO_LIGHT_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withComparedFields("id", "username")
                    .withIgnoredFields("relationTypeOfCurrentUser", "imageUrl")
                    .build();

    private static final RecursiveComparisonConfiguration TO_MODEL_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withIgnoredFields("password", "image", "groups", "notifications", "roles", "experiences", "trainings")
                    .build();
    private static final String[] NULL_FIELDS_FOR_LIGHT_DTO = {
            "firstname",
            "birthday",
            "lastname",
            "url",
            "email",
            "hobbies",
            "purpose",
            "presentation",
            "expectation",
            "contribution",
            "phoneNumber",
            "registeredAt",
            "activatedAt",
            "activationKey",
            "active",
            "relationTypeOfCurrentUser"
    };

    @InjectMocks
    UserAdapter userAdapter;
    @Mock
    WorkExperienceAdapter experienceAdapter;
    @Mock
    TrainingAdapter trainingAdapter;
    @Mock
    FileService fileService;

    @Test
    void should_map_fields_when_toDto() {
        // given
        final User user = generateRandomUser(of(USER));
        user.setTrainings(of(generateRandomTraining()));
        user.setExperiences(of(generateRandomExperience()));
        String imageUrl = "https://appurl/image_key";
        int trainingSize = user.getTrainings().size();
        int experienceSize = user.getExperiences().size();

        given(fileService.buildFileUri(anyString())).willReturn(imageUrl);
        given(experienceAdapter.toDtoWithoutUser(any())).willReturn(WorkExperienceDto.builder().build());
        given(trainingAdapter.toDtoWithoutUser(any())).willReturn(TrainingDto.builder().build());

        // when
        final UserDto userDto = userAdapter.toDto(user);

        // then
        assertThat(userDto)
                .usingRecursiveComparison(TO_DTO_COMPARISON_CONFIGURATION)
                .isEqualTo(user);

        // on vérifie que les rôles sont bien mappés
        assertThat(userDto.getRoles())
                .isNotEmpty()
                .containsSequence(user.getRoles().stream().map(Role::getName).collect(toList()));

        // on vérifie que l'url de l'image est transmise
        assertThat(userDto.getImageUrl())
                .isNotNull()
                .isEqualTo(imageUrl);

        // on vérifie que les expériences ont bien été transmises
        assertThat(userDto.getExperiences())
                .isNotNull()
                .hasSize(experienceSize);

        // on vérifie que les formations ont bien été transmises
        assertThat(userDto.getTrainings())
                .isNotNull()
                .hasSize(trainingSize);

        assertThat(userDto.getRelationTypeOfCurrentUser())
                .isNull();

        verify(experienceAdapter, times(experienceSize)).toDtoWithoutUser(any());
        verify(trainingAdapter, times(trainingSize)).toDtoWithoutUser(any());
        verify(fileService, times(1)).buildFileUri(anyString());
    }

    @Test
    void should_only_map_required_fields_when_toDtoLight() {
        // given
        final User user = generateRandomUser(of(USER));
        String imageUrl = "https://appurl/image_key";

        given(fileService.buildFileUri(anyString())).willReturn(imageUrl);

        // when
        UserDto dtoLight = userAdapter.toDtoLight(user);

        // then
        assertThat(dtoLight)
                .usingRecursiveComparison(TO_DTO_LIGHT_COMPARISON_CONFIGURATION)
                .isEqualTo(user);

        // on vérifie que tous les champs que l'on ne veut pas transmettre son bien nuls
        assertThat(dtoLight)
                .extracting(NULL_FIELDS_FOR_LIGHT_DTO)
                .allSatisfy(field -> assertThat(field).isNull());
    }

    @Test
    void should_map_when_toModel() {
        // given
        final UserDto dto = UserUtils.generateRandomUserDto(of(USER.name()));
        dto.setExperiences(of(generateRandomExperienceDto()));
        dto.setTrainings(of(generateRandomTrainingDto()));
        int experienceCount = dto.getExperiences().size();
        int trainingCount = dto.getTrainings().size();

        given(experienceAdapter.toModel(any())).willReturn(WorkExperience.builder().build());
        given(trainingAdapter.toModel(any())).willReturn(Training.builder().build());

        // when
        final User model = userAdapter.toModel(dto);

        assertThat(model)
                .usingRecursiveComparison(TO_MODEL_COMPARISON_CONFIGURATION)
                .isEqualTo(dto);

        // on vérifie que les expériences ont bien été transmises
        assertThat(model.getExperiences())
                .isNotNull()
                .hasSize(experienceCount);

        // on vérifie que les formations ont bien été transmises
        assertThat(model.getTrainings())
                .isNotNull()
                .hasSize(trainingCount);

        assertThat(model.getRoles())
                .isNotEmpty()
                .doesNotContainNull()
                .allMatch(role -> dto.getRoles().contains(role.getName()));

        verify(experienceAdapter, times(experienceCount)).toModel(any());
        verify(trainingAdapter, times(trainingCount)).toModel(any());
    }
}
