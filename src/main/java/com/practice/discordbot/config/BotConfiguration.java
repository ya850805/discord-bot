package com.practice.discordbot.config;

import com.practice.discordbot.service.EventListener;
import com.practice.discordbot.service.impl.MessageCreateListener;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.List;

/** @author Jason */
@Configuration
public class BotConfiguration {

  @Value("${token}")
  private String token;

  @Bean
  public <T extends Event> GatewayDiscordClient gatewayDiscordClient(
      List<EventListener<T>> eventListeners) {
    DiscordClient client = DiscordClientBuilder.create(token).build();
    

    client.withGateway(gateway -> {
      final Publisher<?> pingPong = gateway.on(MessageCreateEvent.class, event ->
              Mono.just(event.getMessage())
                      .filter(message -> "!ping".equals(message.getContent()))
                      .flatMap(Message::getChannel)
                      .flatMap(channel -> channel.createMessage("Pong!")));

      final Publisher<?> onDisconnect = gateway.onDisconnect()
              .doOnTerminate(() -> System.out.println("Disconnected!"));

      return Mono.when(pingPong, onDisconnect);
    }).block();

    return client.login().block();
  }
}
