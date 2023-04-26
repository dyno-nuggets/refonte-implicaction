package com.dynonuggets.refonteimplicaction.app.service;

import com.dynonuggets.refonteimplicaction.app.dto.App;
import com.dynonuggets.refonteimplicaction.app.dto.enums.AppStatus;
import com.dynonuggets.refonteimplicaction.core.domain.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.feature.service.FeatureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppService {

    private final UserRepository userRepository;
    private final FeatureService featureService;

    public App getApp() {
        return App.builder()
                .status(userRepository.count() > 0 ? AppStatus.INITIALIZED : AppStatus.INITIALIZATION)
                .features(featureService.getAll())
                .build();
    }
}
