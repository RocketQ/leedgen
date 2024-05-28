package ru.leed.leedgen.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.leed.leedgen.service.PollingService;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

  private final PollingService pollingService;

  @EventListener(ContextRefreshedEvent.class)
  public void init() {
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(pollingService);
    } catch(TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
