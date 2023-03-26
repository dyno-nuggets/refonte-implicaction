package com.dynonuggets.refonteimplicaction.user.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserEnabledEvent extends ApplicationEvent {
    private final String username;

    public UserEnabledEvent(final Object source, final String username) {
        super(source);
        this.username = username;
    }
}
