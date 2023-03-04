package com.dynonuggets.refonteimplicaction.community.adapter;

import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.rest.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.rest.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.util.CommunityConstants.DEFAULT_USER_AVATAR_URI;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.*;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class ProfileAdapter {
    private final WorkExperienceAdapter workExperienceAdapter;
    private final TrainingAdapter trainingAdapter;
    private final GroupAdapter groupAdapter;
    private final FileService fileService;

    public ProfileDto toDto(final Profile profile) {
        if (profile == null) {
            return null;
        }

        final List<WorkExperienceDto> experienceDtos = emptyStreamIfNull(profile.getExperiences()).map(workExperienceAdapter::toDto).collect(toList());
        final List<TrainingDto> trainingDtos = emptyStreamIfNull(profile.getTrainings()).map(trainingAdapter::toDto).collect(toList());
        final List<GroupDto> groupDtos = emptyStreamIfNull(profile.getGroups()).map(groupAdapter::toDto).collect(toList());
        final User user = profile.getUser(); // ne peut pas être null par design

        return ProfileDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(getAvatarUrl(profile.getAvatar()))
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .birthday(profile.getBirthday())
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

    public ProfileDto toDtoLight(final Profile profile) {
        if (profile == null) {
            return null;
        }

        final User user = profile.getUser(); // ne peut pas être null par design
        
        return ProfileDto.builder()
                .username(callIfNotNull(user, User::getUsername))
                .email(callIfNotNull(user, User::getEmail))
                .avatar(getAvatarUrl(profile.getAvatar()))
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .build();
    }

    private String getAvatarUrl(final FileModel avatar) {
        return defaultIfNull(fileService.buildFileUri(avatar), DEFAULT_USER_AVATAR_URI);
    }
}
