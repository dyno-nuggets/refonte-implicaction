package com.dynonuggets.refonteimplicaction.community.profile.listener;

import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.user.event.UserEnabledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEnabledListener implements ApplicationListener<UserEnabledEvent> {

    private final ProfileService profileService;

    @Async
    @Override
    public void onApplicationEvent(final UserEnabledEvent event) {
        profileService.createProfile(event.getUsername());
    }
}
