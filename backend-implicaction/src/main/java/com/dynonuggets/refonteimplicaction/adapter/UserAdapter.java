package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
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


@Component
@AllArgsConstructor
public class UserAdapter {

    protected static final String DEFAULT_USER_IMAGE_URI = "assets/img/avatar-ia-user.png";

    private final WorkExperienceAdapter experienceAdapter;
    private final TrainingAdapter trainingAdapter;
    private final CompanyAdapter companyAdapter;
    private final FileService fileService;

    public UserDto toDto(JobSeeker jobSeeker) {
        final User model = jobSeeker.getUser();

        final List<WorkExperienceDto> experiences = isNotEmpty(jobSeeker.getExperiences()) ? jobSeeker.getExperiences()
                .stream()
                .map(experienceAdapter::toDtoWithoutUser)
                .collect(toList()) : emptyList();

        final List<TrainingDto> trainings = isNotEmpty(jobSeeker.getTrainings()) ? jobSeeker.getTrainings()
                .stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(toList()) : emptyList();

        UserDto userDto = toDto(model);
        userDto.setTrainings(trainings);
        userDto.setExperiences(experiences);

        return userDto;
    }

    public UserDto toDto(final Recruiter recruiter) {
        final User model = recruiter.getUser();

        final CompanyDto companyDto = companyAdapter.toDto(recruiter.getCompany());

        UserDto userDto = toDto(model);
        userDto.setCompany(companyDto);

        return userDto;
    }

    public UserDto toDto(final User model) {

        final List<String> roles = rolesToDtos(model);

        final String imageUrl = getImageUrl(model);

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
                .build();
    }

    private String getImageUrl(User model) {
        return model.getImage() != null ? fileService.buildFileUri(model.getImage().getObjectKey()) : DEFAULT_USER_IMAGE_URI;
    }

    public User toModel(UserDto dto) {

        final List<Role> roles = dto.getRoles()
                .stream()
                .map(roleLabel -> {
                    final RoleEnum role = RoleEnum.valueOf(roleLabel);
                    return new Role(role.getId(), role.name(), emptySet());
                })
                .collect(toList());

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .activatedAt(dto.getActivatedAt())
                .activationKey(dto.getActivationKey())
                .active(dto.isActive())
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
