package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.domain.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.community.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingAdapter trainingAdapter;
    private final ProfileService profileService;
    private final AuthService authService;

    @Transactional
    public TrainingDto saveOrUpdateTraining(final TrainingDto trainingDto) {
        final Training training = trainingAdapter.toModel(trainingDto);
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), User::getUsername);
        final Profile profile = profileService.getByUsernameIfExists(currentUsername);
        training.setProfile(profile);
        final Training save = trainingRepository.save(training);
        return trainingAdapter.toDto(save);
    }

    @Transactional
    public void deleteTraining(final Long trainingId) {
        final Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Impossible de supprimer le training, " + trainingId + " n'existe pas."));
        authService.verifyAccessIsGranted(training.getProfile().getUser().getUsername());
        trainingRepository.delete(training);
    }
}
