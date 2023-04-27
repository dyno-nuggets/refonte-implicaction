package com.dynonuggets.refonteimplicaction.core.event;


import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final UserModel user;

    public UserCreatedEvent(final Object source, final UserModel user) {
        super(source);
        this.user = user;
    }
}
