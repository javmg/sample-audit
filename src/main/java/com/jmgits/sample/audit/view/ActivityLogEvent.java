package com.jmgits.sample.audit.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ActivityLogEvent {

    private final String event;

    private final String username;
}
