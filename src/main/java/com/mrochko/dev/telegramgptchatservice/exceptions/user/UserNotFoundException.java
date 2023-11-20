package com.mrochko.dev.telegramgptchatservice.exceptions.user;

/**
 * @author Pavlo Mrochko
 */
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

}
