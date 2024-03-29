package com.dynonuggets.refonteimplicaction.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class EmailDataObject {
    private String subject;
    private List<String> recipients;
}
