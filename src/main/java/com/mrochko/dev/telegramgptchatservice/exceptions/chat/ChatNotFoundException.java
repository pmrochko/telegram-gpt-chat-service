package com.mrochko.dev.telegramgptchatservice.exceptions.chat;

/**
 * @author Pavlo Mrochko
 */
public class ChatNotFoundException extends RuntimeException {

  public ChatNotFoundException(String message) {
    super(message);
  }

}
