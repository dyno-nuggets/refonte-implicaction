package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.repository.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final AuthService authService;
    private final WorkExperienceRepository experienceRepository;
    private final WorkExperienceAdapter experienceAdapter;
    private final UserRepository userRepository;

    @Transactional
    public WorkExperienceDto saveOrUpdateExperience(WorkExperienceDto experienceDto) {
        WorkExperience experience = experienceAdapter.toModel(experienceDto);
        final Long currentUserId = authService.getCurrentUser().getId();

        String operation = experience.getId() != null ? "de modifier" : "d'ajouter";

        final User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Impossible " + operation + " une expérience, l'utilisateur n'existe pas."));

        experience.setUser(user);
        final WorkExperience saved = experienceRepository.save(experience);
        return experienceAdapter.toDtoWithoutUser(saved);
    }

    @Transactional
    public void deleteExperience(Long idToDelete) {

        final Long currentUserId = authService.getCurrentUser().getId();

        WorkExperience workExperience = experienceRepository.findById(idToDelete)
                .orElseThrow(() -> new NotFoundException("Aucune expérience avec l'Id : " + idToDelete + " trouvée."));

        if (!workExperience.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Impossible de supprimer les expériences d'un autre utilisateur.");
        }

        experienceRepository.delete(workExperience);
    }
}
