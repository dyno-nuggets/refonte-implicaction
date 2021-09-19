package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.dto.TrainingDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.Training;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.TrainingRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingAdapter trainingAdapter;
    private final UserRepository userRepository;

    @Transactional
    public List<TrainingDto> updateByUserId(List<TrainingDto> trainingDtos, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Impossible de mettre à jour les formations; L'user avec l'id " + userId + " n'existe pas."));

        List<Training> toUpdateTrainings = trainingDtos.stream()
                .map(trainingDto -> {
                    Training training = trainingAdapter.toModel(trainingDto);
                    training.setUser(user);
                    return training;
                })
                .collect(toList());

        List<Training> allByUserTrainings = trainingRepository.findAllByUser_Id(userId);

        List<Long> toDeleteIds = allByUserTrainings.stream()
                .map(Training::getId)
                .filter(id -> !toUpdateTrainings.stream().map(Training::getId).collect(toList()).contains(id))
                .collect(toList());

        trainingRepository.deleteAllById(toDeleteIds);

        List<Training> trainingsUpdates = trainingRepository.saveAll(toUpdateTrainings);

        return trainingsUpdates.stream()
                .map(trainingAdapter::toDtoWithoutUser)
                .collect(toList());
    }
}
