package com.mrochko.dev.telegramgptchatservice.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pavlo Mrochko
 */
@Data
@NoArgsConstructor
public class UserRegisterDTO {

  private String username;
  private String password;
  private String repeatPassword;

}
