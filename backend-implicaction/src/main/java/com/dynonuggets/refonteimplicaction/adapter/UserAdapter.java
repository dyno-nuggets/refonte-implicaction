package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isTrue;


@Component
@AllArgsConstructor
public class UserAdapter {

    protected static final String DEFAULT_USER_IMAGE_URI = "assets/img/avatar-ia-user.png";

    private final WorkExperienceAdapter experienceAdapter;
    private final TrainingAdapter trainingAdapter;
    private final FileService fileService;

    public UserDto toDto(final User model) {
        final List<String> roles = rolesToDtos(model);
        final String imageUrl = getImageUrl(model);

        final List<WorkExperienceDto> experiences = isNotEmpty(model.getExperiences()) ? model.getExperiences()
                .stream()
                .map(experienceAdapter::toDtoWithoutUser)
                .collect(toList()) : null;

        final List<TrainingDto> trainings = isNotEmpty(model.getTrainings()) ? model.getTrainings()
                .stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(toList()) : null;

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .email(model.getEmail())
                .firstname(model.getFirstname())
                .lastname(model.getLastname())
                .birthday(model.getBirthday())
                .url(model.getUrl())
                .registeredAt(model.getRegisteredAt())
                .activatedAt(model.getActivatedAt())
                .hobbies(model.getHobbies())
                .presentation(model.getPresentation())
                .expectation(model.getExpectation())
                .contribution(model.getContribution())
                .purpose(model.getPurpose())
                .phoneNumber(model.getPhoneNumber())
                .activationKey(model.getActivationKey())
                .active(model.isActive())
                .roles(roles)
                .imageUrl(imageUrl)
                .experiences(experiences)
                .trainings(trainings)
                .build();
    }

    private String getImageUrl(User model) {
        return model.getImage() != null ? fileService.buildFileUri(model.getImage().getObjectKey()) : DEFAULT_USER_IMAGE_URI;
    }

    public User toModel(UserDto dto) {

        // TODO: implémenter callIfNotNull
        final List<WorkExperience> experiences = isNotEmpty(dto.getExperiences()) ? dto.getExperiences()
                .stream()
                .map(experienceAdapter::toModel)
                .collect(toList()) : null;

        // TODO: implémenter callIfNotNull
        final List<Training> trainings = isNotEmpty(dto.getTrainings()) ? dto.getTrainings()
                .stream()
                .map(trainingAdapter::toModel)
                .collect(toList()) : null;

        // TODO: implémenter callIfNotNull
        final List<Role> roles = isNotEmpty(dto.getRoles()) ? dto.getRoles()
                .stream()
                .map(roleLabel -> {
                    final RoleEnum role = RoleEnum.valueOf(roleLabel);
                    return new Role(role.getId(), role.name(), emptySet());
                })
                .collect(toList()) : null;

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .activatedAt(dto.getActivatedAt())
                .activationKey(dto.getActivationKey())
                .active(isTrue(dto.getActive()))
                .contribution(dto.getContribution())
                .expectation(dto.getExpectation())
                .hobbies(dto.getHobbies())
                .presentation(dto.getPresentation())
                .purpose(dto.getPurpose())
                .email(dto.getEmail())
                .url(dto.getUrl())
                .registeredAt(dto.getRegisteredAt())
                .phoneNumber(dto.getPhoneNumber())
                .birthday(dto.getBirthday())
                .roles(roles)
                .experiences(experiences)
                .trainings(trainings)
                .build();
    }

    public UserDto toDtoLight(User model) {
        final List<String> roles = rolesToDtos(model);

        final String imageUrl = model.getImage() != null ? fileService.buildFileUri(model.getImage().getObjectKey()) : null;

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .roles(roles)
                .imageUrl(imageUrl)
                .build();
    }

    private List<String> rolesToDtos(User model) {
        return isNotEmpty(model.getRoles()) ? model.getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList()) : emptyList();
    }
}
