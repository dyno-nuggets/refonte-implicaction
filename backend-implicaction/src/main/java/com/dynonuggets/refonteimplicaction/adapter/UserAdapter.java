package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
@AllArgsConstructor
public class UserAdapter {

    private final WorkExperienceAdapter workExperienceAdapter;
    private final TrainingAdapter trainingAdapter;

    public UserDto toDto(User user) {

        List<WorkExperienceDto> experiencesDtos = user.getExperiences()
                .stream()
                .map(workExperienceAdapter::toDtoWithoutUser)
                .collect(toList());

        List<TrainingDto> trainingDtos = user.getTrainings()
                .stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(toList());

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nicename(user.getNicename())
                .email(user.getEmail())
                .url(user.getUrl())
                .registered(user.getRegistered())
                .status(user.getStatus())
                .displayName(user.getDisplayName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .hobbies(user.getHobbies())
                .experiences(experiencesDtos)
                .trainings(trainingDtos)
                .purpose(user.getPurpose())
                .presentation(user.getPresentation())
                .expectation(user.getExpectation())
                .contribution(user.getContribution())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public UserDto toDtoLight(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nicename(user.getNicename())
                .email(user.getEmail())
                .url(user.getUrl())
                .registered(user.getRegistered())
                .status(user.getStatus())
                .displayName(user.getDisplayName())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();
    }

    public User toModel(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .nicename(userDto.getNicename())
                .email(userDto.getEmail())
                .url(userDto.getUrl())
                .registered(userDto.getRegistered())
                .status(userDto.getStatus())
                .displayName(userDto.getDisplayName())
                .phoneNumber(userDto.getPhoneNumber())
                .birthday(userDto.getBirthday())
                .hobbies(userDto.getHobbies())
                .purpose(userDto.getPurpose())
                .presentation(userDto.getPresentation())
                .expectation(userDto.getExpectation())
                .contribution(userDto.getContribution())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }

}
