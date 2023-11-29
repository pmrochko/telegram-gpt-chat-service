package com.mrochko.dev.telegramgptchatservice.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mrochko.dev.telegramgptchatservice.data.enumeration.Role;
import com.mrochko.dev.telegramgptchatservice.data.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link com.mrochko.dev.telegramgptchatservice.data.entity.User}
 * @author Pavlo Mrochko
 */
@Data
@Builder
public class UserDTO {

  @JsonProperty(access = Access.READ_ONLY)
  Long id;

  @NotBlank
  String username;

  String password;

  @ValueOfEnum(enumClass = Role.class)
  Role role;

}
