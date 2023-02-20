package com.dynonuggets.refonteimplicaction.auth.adapter;

import com.dynonuggets.refonteimplicaction.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.service.FileService;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtilTest.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtilTest.generateRandomUserDto;
import static com.dynonuggets.refonteimplicaction.utils.ExperienceUtils.generateRandomExperience;
import static com.dynonuggets.refonteimplicaction.utils.ExperienceUtils.generateRandomExperienceDto;
import static com.dynonuggets.refonteimplicaction.utils.TrainingUtils.generateRandomTraining;
import static com.dynonuggets.refonteimplicaction.utils.TrainingUtils.generateRandomTrainingDto;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserAdapterTest {

    static final RecursiveComparisonConfiguration TO_DTO_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withIgnoredFields("imageUrl", "roles", "experiences", "trainings", "relationTypeOfCurrentUser")
                    .build();
    static final RecursiveComparisonConfiguration TO_DTO_LIGHT_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withComparedFields("id", "username")
                    .withIgnoredFields("relationTypeOfCurrentUser", "imageUrl")
                    .build();
    static final RecursiveComparisonConfiguration TO_MODEL_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withIgnoredFields("password", "image", "groups", "notifications", "roles", "experiences", "trainings")
                    .build();

    static final String[] NULL_FIELDS_FOR_LIGHT_DTO = {
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
            "activationKey",
            "relationTypeOfCurrentUser"
    };

    User mockedUser;
    UserDto mockedUserDto;

    @InjectMocks
    UserAdapter userAdapter;
    @Mock
    WorkExperienceAdapter experienceAdapter;
    @Mock
    TrainingAdapter trainingAdapter;
    @Mock
    FileService fileService;

    @BeforeEach
    void setUp() {
        mockedUser = generateRandomUser(of(USER), true);
        mockedUserDto = generateRandomUserDto(of(USER.name()), true);
    }

    @Test
    @DisplayName("doit retourner un dto correspondant au model fourni quand on utilise toDto")
    void should_map_fields_when_toDto() {
        // given
        mockedUser.setTrainings(of(generateRandomTraining()));
        mockedUser.setExperiences(of(generateRandomExperience()));
        final String imageUrl = "https://appurl/image_key";
        mockedUser.setImage(FileModel.builder().objectKey("image_key").build());
        final int trainingSize = mockedUser.getTrainings().size();
        final int experienceSize = mockedUser.getExperiences().size();

        given(fileService.buildFileUri(anyString())).willReturn(imageUrl);
        given(experienceAdapter.toDtoWithoutUser(any())).willReturn(WorkExperienceDto.builder().build());
        given(trainingAdapter.toDtoWithoutUser(any())).willReturn(TrainingDto.builder().build());

        // when
        final UserDto userDto = userAdapter.toDto(mockedUser);

        // then
        assertThat(userDto)
                .usingRecursiveComparison(TO_DTO_COMPARISON_CONFIGURATION)
                .isEqualTo(mockedUser);

        // on vérifie que les rôles sont bien mappés
        assertThat(userDto)
                .extracting(UserDto::getRoles).asList()
                .isNotEmpty()
                .containsSequence(mockedUser.getRoles().stream().map(Role::getName).collect(toList()));

        // on vérifie que l'url de l'image est transmise
        assertThat(userDto)
                .extracting(UserDto::getImageUrl)
                .isEqualTo(imageUrl);

        // on vérifie que les expériences ont bien été transmises
        assertThat(userDto)
                .extracting(UserDto::getExperiences).asList()
                .hasSize(experienceSize);

        // on vérifie que les formations ont bien été transmises
        assertThat(userDto)
                .extracting(UserDto::getTrainings).asList()
                .hasSize(trainingSize);

        assertThat(userDto)
                .extracting(UserDto::getRelationTypeOfCurrentUser)
                .isNull();

        verify(experienceAdapter, times(experienceSize)).toDtoWithoutUser(any());
        verify(trainingAdapter, times(trainingSize)).toDtoWithoutUser(any());
        verify(fileService, times(1)).buildFileUri(anyString());
    }

    @Test
    @DisplayName("doit retourner un dto avec seulement les champs requis correspondants au model fourni quand on utilise toDtoLight")
    void should_only_map_required_fields_when_toDtoLight() {
        // given
        final String imageUrl = "https://appurl/image_key";
        mockedUser.setImage(FileModel.builder().build());
        given(fileService.buildFileUri(any())).willReturn(imageUrl);

        // when
        final UserDto dtoLight = userAdapter.toDtoLight(mockedUser);

        // then
        assertThat(dtoLight)
                .usingRecursiveComparison(TO_DTO_LIGHT_COMPARISON_CONFIGURATION)
                .isEqualTo(mockedUser);

        // on vérifie que tous les champs que l'on ne veut pas transmettre son bien nuls NULL_FIELDS_FOR_LIGHT_DTO
        assertThat(dtoLight)
                .extracting(NULL_FIELDS_FOR_LIGHT_DTO)
                .allSatisfy(field -> assertThat(field).isNull());
    }

    @Test
    @DisplayName("doit retourner un model correspondant au dto fourni quand on utilise toModel")
    void should_map_when_toModel() {
        // given
        mockedUserDto.setExperiences(of(generateRandomExperienceDto()));
        mockedUserDto.setTrainings(of(generateRandomTrainingDto()));
        final int experienceCount = mockedUserDto.getExperiences().size();
        final int trainingCount = mockedUserDto.getTrainings().size();
        given(experienceAdapter.toModel(any())).willReturn(WorkExperience.builder().build());
        given(trainingAdapter.toModel(any())).willReturn(Training.builder().build());

        // when
        final User model = userAdapter.toModel(mockedUserDto);

        // vérification champ par champ
        assertThat(model)
                .usingRecursiveComparison(TO_MODEL_COMPARISON_CONFIGURATION)
                .isEqualTo(mockedUserDto);

        // on vérifie que les expériences ont bien été transmises
        assertThat(model)
                .extracting(User::getExperiences).asList()
                .hasSize(experienceCount);

        // on vérifie que les formations ont bien été transmises
        assertThat(model)
                .extracting(User::getTrainings).asList()
                .hasSize(trainingCount);

        // on vérifie ques les rôles correspondent
        assertThat(model)
                .extracting(User::getRoles).asList().map(o -> (Role) o)
                .allMatch(role -> mockedUserDto.getRoles().contains(role.getName()));

        verify(experienceAdapter, times(experienceCount)).toModel(any());
        verify(trainingAdapter, times(trainingCount)).toModel(any());
    }
}
