package com.dynonuggets.refonteimplicaction.community.profile.listener;

import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.event.UserEnabledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEnabledListener implements ApplicationListener<UserEnabledEvent> {

    private final ProfileService profileService;

    @Override
    public void onApplicationEvent(final UserEnabledEvent event) {
        profileService.createProfile(event.getUsername());
    }
}
