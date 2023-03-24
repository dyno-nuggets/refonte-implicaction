package com.dynonuggets.refonteimplicaction.community.workexperience.sercice;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.community.workexperience.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.workexperience.domain.repository.WorkExperienceRepository;
import com.dynonuggets.refonteimplicaction.community.workexperience.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final AuthService authService;
    private final WorkExperienceRepository experienceRepository;
    private final WorkExperienceAdapter experienceAdapter;
    private final ProfileService profileService;

    @Transactional
    public WorkExperienceDto saveOrUpdateExperience(final WorkExperienceDto experienceDto) {
        final WorkExperience experience = experienceAdapter.toModel(experienceDto);
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final Profile user = profileService.getByUsernameIfExists(currentUsername);
        experience.setProfile(user);
        final WorkExperience saved = experienceRepository.save(experience);
        return experienceAdapter.toDto(saved);
    }

    @Transactional
    public void deleteExperience(final Long idToDelete) {

        final Long currentUserId = authService.getCurrentUser().getId();

        final WorkExperience workExperience = experienceRepository.findById(idToDelete)
                .orElseThrow(() -> new NotFoundException("Aucune expérience avec l'Id : " + idToDelete + " trouvée."));

        if (!workExperience.getProfile().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Impossible de supprimer les expériences d'un autre utilisateur.");
        }

        experienceRepository.delete(workExperience);
    }
}
