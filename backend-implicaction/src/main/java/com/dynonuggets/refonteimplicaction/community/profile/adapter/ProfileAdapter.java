package com.dynonuggets.refonteimplicaction.community.profile.adapter;

import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.mapper.GroupMapper;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.training.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.workexperience.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class ProfileAdapter {
    private final WorkExperienceAdapter workExperienceAdapter;
    private final TrainingAdapter trainingAdapter;
    private final GroupMapper groupMapper;

    public ProfileDto toDto(final ProfileModel profile) {
        if (profile == null) {
            return null;
        }

        final List<WorkExperienceDto> experienceDtos = emptyStreamIfNull(profile.getExperiences()).map(workExperienceAdapter::toDto).collect(toList());
        final List<TrainingDto> trainingDtos = emptyStreamIfNull(profile.getTrainings()).map(trainingAdapter::toDto).collect(toList());
        final List<GroupDto> groupDtos = emptyStreamIfNull(profile.getGroups()).map(groupMapper::toDto).collect(toList());
        final UserModel user = profile.getUser();
        final ProfileDto.ProfileDtoBuilder builder = ProfileDto.builder();

        if (user != null) {
            builder
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .registeredAt(user.getRegisteredAt())
                    .email(user.getEmail())
                    .birthday(user.getBirthday());
        }

        return builder
                .imageUrl(profile.getImageUrl())
                .hobbies(profile.getHobbies())
                .purpose(profile.getPurpose())
                .presentation(profile.getPresentation())
                .expectation(profile.getExpectation())
                .contribution(profile.getContribution())
                .phoneNumber(profile.getPhoneNumber())
                .experiences(experienceDtos)
                .trainings(trainingDtos)
                .groups(groupDtos)
                .build();
    }

    public ProfileDto toDtoLight(final ProfileModel profile) {
        if (profile == null) {
            return null;
        }

        final UserModel user = profile.getUser();

        return ProfileDto.builder()
                .username(callIfNotNull(user, UserModel::getUsername))
                .firstname(callIfNotNull(user, UserModel::getFirstname))
                .lastname(callIfNotNull(user, UserModel::getLastname))
                .email(callIfNotNull(user, UserModel::getEmail))
                .username(callIfNotNull(user, UserModel::getUsername))
                .imageUrl(profile.getImageUrl())
                .build();
    }
}
