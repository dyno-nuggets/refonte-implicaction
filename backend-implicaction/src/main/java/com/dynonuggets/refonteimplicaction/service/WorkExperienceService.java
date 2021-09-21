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

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceAdapter workExperienceAdapter;
    private final UserRepository userRepository;

    public List<WorkExperienceDto> updateByUserId(List<WorkExperienceDto> workExperienceDtos, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Impossible de mettre à jour les expériences professionnelles; L'user avec l'id " + userId + " n'existe pas."));

        List<WorkExperience> toUpdateExperiences = workExperienceDtos.stream()
                .map(workExperienceDto -> {
                    WorkExperience workExperience = workExperienceAdapter.toModel(workExperienceDto);
                    workExperience.setUser(user);
                    return workExperience;
                })
                .collect(toList());

        // On isole les expériences professionnelles à supprimer en comparant avec les id celles envoyées à celles en base
        List<WorkExperience> allByUserExperiences = workExperienceRepository.findAllByUser_Id(userId);

        List<Long> toDeleteIds = allByUserExperiences.stream()
                .map(WorkExperience::getId)
                .filter(id -> !toUpdateExperiences.stream().map(WorkExperience::getId).collect(toList()).contains(id))
                .collect(toList());
        workExperienceRepository.deleteAllById(toDeleteIds);

        List<WorkExperience> experiencesUpdates = workExperienceRepository.saveAll(toUpdateExperiences);

        return experiencesUpdates.stream()
                .map(workExperienceAdapter::toDtoWithoutUser)
                .collect(toList());
    }
}
