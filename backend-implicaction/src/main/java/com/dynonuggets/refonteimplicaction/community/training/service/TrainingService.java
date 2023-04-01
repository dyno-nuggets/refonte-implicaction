package com.dynonuggets.refonteimplicaction.community.training.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.community.training.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.community.training.domain.model.Training;
import com.dynonuggets.refonteimplicaction.community.training.domain.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;

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
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(currentUsername);
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
