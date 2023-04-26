package com.dynonuggets.refonteimplicaction.auth.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IntialUserCreateEvent extends ApplicationEvent {
    private final String username;

    public IntialUserCreateEvent(final Object source, final String username) {
        super(source);
        this.username = username;
    }
}
