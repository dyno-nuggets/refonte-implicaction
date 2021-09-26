package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.dto.WorkExperienceDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.WorkExperience;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.repository.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceAdapter workExperienceAdapter;
    private final UserRepository userRepository;

    public WorkExperienceDto updateByUserId(WorkExperienceDto experienceDto, Long userId) {
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
}
