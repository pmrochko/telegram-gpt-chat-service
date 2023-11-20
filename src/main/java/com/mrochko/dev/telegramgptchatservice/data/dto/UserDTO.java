package com.mrochko.dev.telegramgptchatservice.data.dto;

import com.mrochko.dev.telegramgptchatservice.data.enumeration.Role;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author Pavlo Mrochko
 * DTO for {@link com.mrochko.dev.telegramgptchatservice.data.entity.User}
 */
@Data
@Builder
public class UserDTO implements Serializable {

  Long id;
  String username;
  String password;
  Role role;

}
