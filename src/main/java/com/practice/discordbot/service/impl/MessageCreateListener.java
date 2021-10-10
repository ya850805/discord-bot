package com.practice.discordbot.service.impl;

import com.practice.discordbot.service.EventListener;
import com.practice.discordbot.service.MessageListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/** @author Jason */
@Slf4j
@Service
public class MessageCreateListener extends MessageListener
    implements EventListener<MessageCreateEvent> {

  @Override
  public Class<MessageCreateEvent> getEventType() {
    return MessageCreateEvent.class;
  }

  @Override
  public Mono<Void> execute(MessageCreateEvent event) {
    log.info("xxx");
    return processCommand(event.getMessage());
  }
}
