package com.mrochko.dev.telegramgptchatservice.data.constants;

import lombok.Getter;

/**
 * @author Pavlo Mrochko
 */
public final class GptRequestConstants {

  public static final String AUTHORIZATION = "Authorization";
  public static final String MODEL = "model";
  public static final String MESSAGES = "messages";
  public static final String TEMPERATURE = "temperature";
  public static final String MAX_TOKENS = "max_tokens";
  public static final String TOP_P = "top_p";
  public static final String FREQUENCY_PENALTY = "frequency_penalty";
  public static final String PRESENCE_PENALTY = "presence_penalty";
  public static final String ROLE = "role";
  public static final String CONTENT = "content";

  @Getter
  public enum MessagesRole {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    TOOL("tool");

    private final String value;

    MessagesRole(String value) {
      this.value = value;
    }

  }

}
