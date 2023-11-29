package com.mrochko.dev.telegramgptchatservice.data.dto;

import com.mrochko.dev.telegramgptchatservice.data.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for request registration form
 * @author Pavlo Mrochko
 */
@Data
@NoArgsConstructor
public class UserRegisterDTO {

  @NotBlank
  private String username;

  @ValidPassword(message = "Password must contain at least one digit, "
      + "one lowercase and one uppercase letter, "
      + "and be at least 8 characters long")
  private String password;

  private String repeatPassword;

}
