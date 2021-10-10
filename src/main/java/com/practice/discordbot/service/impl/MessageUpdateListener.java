package com.practice.discordbot.service.impl;

import com.practice.discordbot.service.EventListener;
import com.practice.discordbot.service.MessageListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/** @author Jason */
@Slf4j
@Service
public class MessageUpdateListener extends MessageListener
    implements EventListener<MessageUpdateEvent> {

  @Override
  public Class<MessageUpdateEvent> getEventType() {
    return MessageUpdateEvent.class;
  }

  @Override
  public Mono<Void> execute(MessageUpdateEvent event) {
    log.info("xxx");
    return Mono.just(event)
        .filter(MessageUpdateEvent::isContentChanged)
        .flatMap(MessageUpdateEvent::getMessage)
        .flatMap(super::processCommand);
  }
}
