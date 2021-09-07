package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class UserAdapter {

    private final WorkExperienceAdapter workExperienceAdapter;
    private final TrainingAdapter trainingAdapter ;

    public UserDto toDto(User user) {
        
        List<WorkExperienceDto> experiencesDtos = user.getExperiences()
                .stream()
                .map(workExperienceAdapter::toDtoWithoutUser)
                .collect(Collectors.toList());

        List<TrainingDto> trainingDtos = user.getTrainings()
                .stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(Collectors.toList());

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
                .build();
    }

}
