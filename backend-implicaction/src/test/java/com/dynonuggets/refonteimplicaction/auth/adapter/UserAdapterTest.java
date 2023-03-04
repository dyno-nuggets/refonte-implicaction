package com.dynonuggets.refonteimplicaction.auth.adapter;

import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtilTest.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.auth.utils.UserUtilTest.generateRandomUserDto;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

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
                    .withIgnoredFields("password", "notifications", "roles")
                    .build();

    static final String[] NULL_FIELDS_FOR_LIGHT_DTO = {
            "email",
            "registeredAt",
            "activationKey",
    };

    User mockedUser;
    UserDto mockedUserDto;

    @InjectMocks
    UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        mockedUser = generateRandomUser(of(USER), true);
        mockedUserDto = generateRandomUserDto(of(USER.name()), true);
    }

    @Nested
    @DisplayName("# toDto")
    class ToDtoTest {
        @Test
        @DisplayName("doit retourner un dto correspondant au model fourni quand on utilise toDto")
        void should_map_fields_when_toDto() {
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
        }

        @Test
        @DisplayName("doit retourner null quand on utilise toDto et que le model est null")
        void should_return_null_when_toDto_and_model_is_null() {
            assertThat(userAdapter.toDto(null)).isNull();
        }
    }

    @Nested
    @DisplayName("# toDtoLight")
    class ToDtoLightTest {
        @Test
        @DisplayName("doit retourner un dto avec seulement les champs requis correspondants au model fourni quand on utilise toDtoLight")
        void should_only_map_required_fields_when_toDtoLight() {
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
        @DisplayName("doit retourner null quand on utilise toDtoLight et que le model est null")
        void should_return_null_when_toDtoLight_and_model_is_null() {
            assertThat(userAdapter.toDtoLight(null)).isNull();
        }
    }

    @Nested
    @DisplayName("# toModel")
    class ToModelTest {
        @Test
        @DisplayName("doit retourner un model correspondant au dto fourni quand on utilise toModel")
        void should_map_when_toModel() {
            // when
            final User model = userAdapter.toModel(mockedUserDto);

            // vérification champ par champ
            assertThat(model)
                    .usingRecursiveComparison(TO_MODEL_COMPARISON_CONFIGURATION)
                    .isEqualTo(mockedUserDto);

            // on vérifie ques les rôles correspondent
            assertThat(model)
                    .extracting(User::getRoles).asList().map(o -> (Role) o)
                    .allMatch(role -> mockedUserDto.getRoles().contains(role.getName()));
        }

        @Test
        @DisplayName("doit retourner null quand on utilise toModel et que le dto est null")
        void should_return_null_when_toDtoLight_and_model_is_null() {
            assertThat(userAdapter.toModel(null)).isNull();
        }
    }
}
