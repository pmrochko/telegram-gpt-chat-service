package com.mrochko.dev.telegramgptchatservice.data.mapper;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserDTO;
import com.mrochko.dev.telegramgptchatservice.data.dto.UserRegisterDTO;
import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Pavlo Mrochko
 */
@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true)
  User dtoToUserEntity(UserDTO userDTO);

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "role", ignore = true)
  })
  User registerDtoToUserEntity(UserRegisterDTO UserRegisterDTO);

  UserDTO userEntityToDto(User user);

  List<UserDTO> userListToDtoList(List<User> userList);

}
