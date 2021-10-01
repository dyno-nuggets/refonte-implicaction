package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingAdapter trainingAdapter;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public TrainingDto saveOrUpdateTraining(final TrainingDto trainingDto) {
        Training training = trainingAdapter.toModel(trainingDto);
        final Long currentUserId = authService.getCurrentUser().getId();
        String operation = training.getId() != null ? "de modifier" : "d'ajouter";
        final User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Impossible " + operation + " une formation, l'utilisateur n'existe pas."));
        training.setUser(user);
        final Training save = trainingRepository.save(training);
        return trainingAdapter.toDtoWithoutUser(save);
    }

    @Transactional
    public void deleteTraining(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Impossible de supprimer le training, " + trainingId + " n'existe pas."));
        final Long currentUserId = authService.getCurrentUser().getId();
        if (!training.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Impossible de supprimer la formation, utilisateur non autorisé.");
        }
        trainingRepository.delete(training);
    }
}
