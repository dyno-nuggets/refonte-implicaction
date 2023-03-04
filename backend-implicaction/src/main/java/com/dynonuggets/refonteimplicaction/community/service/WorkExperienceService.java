package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.community.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.domain.repository.WorkExperienceRepository;
import com.dynonuggets.refonteimplicaction.community.rest.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final AuthService authService;
    private final WorkExperienceRepository experienceRepository;
    private final WorkExperienceAdapter experienceAdapter;
    private final ProfileRepository profileRepository;

    @Transactional
    public WorkExperienceDto saveOrUpdateExperience(final WorkExperienceDto experienceDto) {
        final WorkExperience experience = experienceAdapter.toModel(experienceDto);
        final Long currentUserId = authService.getCurrentUser().getId();

        final String operation = experience.getId() != null ? "de modifier" : "d'ajouter";

        final Profile user = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Impossible " + operation + " une expérience, l'utilisateur n'existe pas."));

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
