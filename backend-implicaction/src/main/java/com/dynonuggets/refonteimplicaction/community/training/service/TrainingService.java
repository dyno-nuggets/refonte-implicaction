package com.dynonuggets.refonteimplicaction.community.training.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.community.training.domain.model.TrainingModel;
import com.dynonuggets.refonteimplicaction.community.training.domain.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.community.training.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.community.training.mapper.TrainingMapper;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;

@Service
@AllArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ProfileService profileService;
    private final AuthService authService;

    @Transactional
    public TrainingDto saveOrUpdateTraining(final TrainingDto trainingDto) {
        final TrainingModel training = trainingMapper.toModel(trainingDto);
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);
        final ProfileModel profile = profileService.getByUsernameIfExistsAndUserEnabled(currentUsername);
        training.setProfile(profile);
        final TrainingModel save = trainingRepository.save(training);
        return trainingMapper.toDto(save);
    }

    @Transactional
    public void deleteTraining(final Long trainingId) {
        final TrainingModel training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Impossible de supprimer le training, " + trainingId + " n'existe pas."));
        authService.verifyAccessIsGranted(training.getProfile().getUser().getUsername());
        trainingRepository.delete(training);
    }
}
