package com.mrochko.dev.telegramgptchatservice.data.mapper;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserDTO;
import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Pavlo Mrochko
 */
@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true)
  User dtoToEntity(UserDTO userDTO);

  UserDTO entityToDto(User user);

}
