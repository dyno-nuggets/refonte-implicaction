package com.dynonuggets.refonteimplicaction.user.mapper;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum.USER;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUserDto;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    static final RecursiveComparisonConfiguration TO_DTO_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withIgnoredFields("imageUrl", "roles", "experiences", "trainings", "relationTypeOfCurrentUser")
                    .build();
    static final RecursiveComparisonConfiguration TO_DTO_LIGHT_COMPARISON_CONFIGURATION =
            RecursiveComparisonConfiguration.builder()
                    .withComparedFields("id", "username")
                    .withIgnoredFields("relationTypeOfCurrentUser", "imageUrl")
                    .build();

    static final String[] NULL_FIELDS_FOR_LIGHT_DTO = {
            "email",
            "registeredAt",
            "activationKey",
            "firstname",
            "lastname",
            "birthday",
            "email",
            "registeredAt",
            "activationKey",
            "enabled",
            "emailVerified"
    };

    UserModel mockedUser;
    UserDto mockedUserDto;

    public UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @BeforeEach
    void setUp() {
        mockedUser = generateRandomUser(Set.of(USER), true);
        mockedUserDto = generateRandomUserDto(Set.of(USER.name()), true);
    }

    @Nested
    @DisplayName("# toDto")
    class ToDtoTest {
        @Test
        @DisplayName("doit retourner un dto correspondant au model fourni quand on utilise toDto")
        void should_map_fields_when_toDto() {
            // when
            final UserDto userDto = mapper.toDto(mockedUser);

            // then
            assertThat(userDto)
                    .usingRecursiveComparison(TO_DTO_COMPARISON_CONFIGURATION)
                    .isEqualTo(mockedUser);

            // on vérifie que les rôles sont bien mappés
            assertThat(userDto.getRoles())
                    .isNotEmpty()
                    .containsSequence(mockedUser.getRoles().stream().map(Role::getName).collect(toList()));
        }

        @Test
        @DisplayName("doit retourner null quand on utilise toDto et que le model est null")
        void should_return_null_when_toDto_and_model_is_null() {
            assertThat(mapper.toDto(null)).isNull();
        }
    }

    @Nested
    @DisplayName("# toDtoLight")
    class ToDtoLightTest {
        @Test
        @DisplayName("doit retourner un dto avec seulement les champs requis correspondants au model fourni quand on utilise toDtoLight")
        void should_only_map_required_fields_when_toDtoLight() {
            // when
            final UserDto dtoLight = mapper.toDtoLight(mockedUser);

            // then
            assertThat(dtoLight)
                    .usingRecursiveComparison(TO_DTO_LIGHT_COMPARISON_CONFIGURATION)
                    .isEqualTo(mockedUser);

            // on vérifie que tous les champs que l’on ne veut pas transmettre sont bien nuls NULL_FIELDS_FOR_LIGHT_DTO
            assertThat(dtoLight)
                    .extracting(NULL_FIELDS_FOR_LIGHT_DTO)
                    .allSatisfy(field -> assertThat(field).isNull());
        }

        @Test
        @DisplayName("doit retourner null quand on utilise toDtoLight et que le model est null")
        void should_return_null_when_toDtoLight_and_model_is_null() {
            assertThat(mapper.toDtoLight(null)).isNull();
        }
    }
}
