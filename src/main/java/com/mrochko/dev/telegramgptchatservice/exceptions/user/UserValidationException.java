package com.mrochko.dev.telegramgptchatservice.exceptions.user;

/**
 * @author Pavlo Mrochko
 */
public class UserValidationException extends RuntimeException {

  public UserValidationException(String message) {
    super(message);
  }

}
