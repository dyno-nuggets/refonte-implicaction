package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.repository.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final AuthService authService;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceAdapter workExperienceAdapter;
    private final UserRepository userRepository;
    private final AuthService authService;

    public WorkExperienceDto updateByUserId(WorkExperienceDto experienceDto, Long userId) {

        final UserDto currentUser = authService.getCurrentUser();

        // on autorise la modification des expériences seulement à leur propriétaire
        if (!currentUser.getId().equals(userId)) {
            throw new UnauthorizedException("Impossible de modifier une expérience d'un autre utilisateur");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Impossible de mettre à jour une expérience professionnelle; L'user avec l'id " + userId + " n'existe pas."));

        WorkExperience workExperience = workExperienceAdapter.toModel(experienceDto);
        workExperience.setUser(user);

        final WorkExperience experienceSaved = workExperienceRepository.save(workExperience);

        return workExperienceAdapter.toDtoWithoutUser(experienceSaved);
    }

    public WorkExperienceDto createByUserId(WorkExperienceDto workExperienceDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Impossible créer l'expérience professionnelle; L'user avec l'id " + userId + " n'existe pas."));
        WorkExperience workExperience = workExperienceAdapter.toModel(workExperienceDto);
        workExperience.setUser(user);
        final WorkExperience created = workExperienceRepository.save(workExperience);
        return workExperienceAdapter.toDtoWithoutUser(created);
    }

    public void deleteExperience(Long idToDelete) {

        final Long currentUserId = authService.getCurrentUser().getId();

        WorkExperience workExperience = workExperienceRepository.findById(idToDelete)
                .orElseThrow(() -> new NotFoundException("Aucune expérience avec l'Id : " + idToDelete + " trouvée."));

        if (!workExperience.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Impossible de supprimer les expériences d'un autre utilisateur.");
        }

        workExperienceRepository.delete(workExperience);
    }
}
