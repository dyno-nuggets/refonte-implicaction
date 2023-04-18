package com.dynonuggets.refonteimplicaction.community.workexperience.sercice;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.community.workexperience.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.repository.WorkExperienceRepository;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.community.workexperience.error.WorkExperienceErrorResult.XP_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static java.lang.String.valueOf;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final AuthService authService;
    private final WorkExperienceRepository experienceRepository;
    private final WorkExperienceAdapter experienceAdapter;
    private final ProfileService profileService;

    @Transactional
    public WorkExperienceDto saveOrUpdateExperience(final WorkExperienceDto experienceDto) {
        final WorkExperienceModel experience = experienceAdapter.toModel(experienceDto);
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final ProfileModel user = profileService.getByUsernameIfExistsAndUserEnabled(currentUsername);
        experience.setProfile(user);
        final WorkExperienceModel saved = experienceRepository.save(experience);
        return experienceAdapter.toDto(saved);
    }

    @Transactional
    public void deleteExperience(final Long id) {
        final Long currentUserId = authService.getCurrentUser().getId();
        final WorkExperienceModel workExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(XP_NOT_FOUND, valueOf(id)));

        if (!workExperience.getProfile().getId().equals(currentUserId)) {
            throw new CoreException(OPERATION_NOT_PERMITTED);
        }

        experienceRepository.delete(workExperience);
    }
}
