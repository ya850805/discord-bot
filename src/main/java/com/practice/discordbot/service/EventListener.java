package com.practice.discordbot.service;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

/**
 * @author Jason
 */
public interface EventListener<T extends Event> {

    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
    //        LOG.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}
