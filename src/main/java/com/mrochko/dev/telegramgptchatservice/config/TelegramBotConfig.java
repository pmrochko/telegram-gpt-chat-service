package com.mrochko.dev.telegramgptchatservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pavlo Mrochko
 */
@Configuration
@Getter
public class TelegramBotConfig {

  @Value("${telegram-bot-api.name}")
  String botName;

  @Value("${telegram-bot-api.token}")
  String botToken;

}
