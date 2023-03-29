package com.dynonuggets.refonteimplicaction.community.training.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TrainingUris {
    public static final String TRAINING_BASE_URI = "/api/trainings";
    public static final String DELETE_TRAINING_URI = "/{trainingId}";
}
