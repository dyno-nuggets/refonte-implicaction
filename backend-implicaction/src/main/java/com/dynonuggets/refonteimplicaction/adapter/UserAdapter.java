package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.CompanyDto;
import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.*;
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

    private final WorkExperienceAdapter experienceAdapter;
    private final TrainingAdapter trainingAdapter;
    private final CompanyAdapter companyAdapter;

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
                .build();
    }

    public User toModel(UserDto dto) {

        final List<Role> roles = dto.getRoles()
                .stream()
                .map(roleLabel -> {
                    final RoleEnum role = RoleEnum.valueOf(roleLabel);
                    return new Role(role.getId(), role.getName(), emptySet());
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

        return UserDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .roles(roles)
                .build();
    }

    private List<String> rolesToDtos(User model) {
        return isNotEmpty(model.getRoles()) ? model.getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList()) : emptyList();
    }
}
