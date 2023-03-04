package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.domain.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.community.rest.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingAdapter trainingAdapter;
    private final ProfileRepository profileRepository;
    private final AuthService authService;

    @Transactional
    public TrainingDto saveOrUpdateTraining(final TrainingDto trainingDto) {
        final Training training = trainingAdapter.toModel(trainingDto);
        final Long currentUserId = authService.getCurrentUser().getId();
        final String operation = training.getId() != null ? "de modifier" : "d'ajouter";
        final Profile profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Impossible " + operation + " une formation, l'utilisateur n'existe pas."));
        training.setProfile(profile);
        final Training save = trainingRepository.save(training);
        return trainingAdapter.toDto(save);
    }

    @Transactional
    public void deleteTraining(final Long trainingId) {
        final Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Impossible de supprimer le training, " + trainingId + " n'existe pas."));
        final Long currentUserId = authService.getCurrentUser().getId();

        // TODO: utiliser une exception adequate
        if (!training.getProfile().getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Impossible de supprimer la formation, utilisateur non autorisé.");
        }

        trainingRepository.delete(training);
    }
}
