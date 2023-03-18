package com.dynonuggets.refonteimplicaction.core.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class EmailObject {
    private String subject;
    private List<String> recipients;
}
