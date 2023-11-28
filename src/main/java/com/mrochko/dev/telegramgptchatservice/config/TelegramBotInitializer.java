package com.mrochko.dev.telegramgptchatservice.config;

import com.mrochko.dev.telegramgptchatservice.bot.TelegramGptChatBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author Pavlo Mrochko
 */
@Component
@RequiredArgsConstructor
public class TelegramBotInitializer {

  private final TelegramGptChatBot telegramBot;

  @EventListener(ContextRefreshedEvent.class)
  public void init() throws TelegramApiException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    try{
      telegramBotsApi.registerBot(telegramBot);
    } catch (TelegramApiException e){
      e.printStackTrace();
    }
  }

}
