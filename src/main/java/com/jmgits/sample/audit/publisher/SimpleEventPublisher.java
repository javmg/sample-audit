package com.jmgits.sample.audit.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Object event){

        applicationEventPublisher.publishEvent(event);
    }
}
