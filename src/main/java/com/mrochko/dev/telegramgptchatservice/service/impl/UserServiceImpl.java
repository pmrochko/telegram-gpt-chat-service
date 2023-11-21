package com.mrochko.dev.telegramgptchatservice.service.impl;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserDTO;
import com.mrochko.dev.telegramgptchatservice.data.dto.UserRegisterDTO;
import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import com.mrochko.dev.telegramgptchatservice.data.enumeration.Role;
import com.mrochko.dev.telegramgptchatservice.data.mapper.UserMapper;
import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserNotFoundException;
import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserValidationException;
import com.mrochko.dev.telegramgptchatservice.repository.UserRepository;
import com.mrochko.dev.telegramgptchatservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Pavlo Mrochko
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final static String PASSWORDS_NOT_EQUAL_MESSAGE = "Passwords are not equal";
  private final static String USERNAME_ALREADY_TAKEN_MESSAGE = "Username is already taken";
  private final static String USER_NOT_FOUND_MESSAGE = "User was not found by the specified ID";
  private final static String USER_ID_PROPERTY = "id";
  private final static String MAIN_ADMIN_USERNAME = "admin";

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerUser(UserRegisterDTO userRegisterDTO) {
    if (!areEqualPasswords(userRegisterDTO.getPassword(), userRegisterDTO.getRepeatPassword()))
      throw new UserValidationException(PASSWORDS_NOT_EQUAL_MESSAGE);

    if (userRepository.existsUserByUsername(userRegisterDTO.getUsername()))
      throw new UserValidationException(USERNAME_ALREADY_TAKEN_MESSAGE);

    String encodedPass = passwordEncoder.encode(userRegisterDTO.getPassword());
    User newUser = UserMapper.INSTANCE.registerDtoToUserEntity(userRegisterDTO);
    newUser.setPassword(encodedPass);
    newUser.setRole(Role.USER);
    userRepository.save(newUser);
  }

  @Override
  public List<UserDTO> getAllUsersSortedById() {
    return UserMapper.INSTANCE.userListToDtoList(
        userRepository.findAll(Sort.by(USER_ID_PROPERTY)));
  }

  @Override
  public void updateUserRole(Authentication auth, Long userId, String newRole) {
    org.springframework.security.core.userdetails.User authenticatedUser =
        (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    User userForEditRole = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

    validatePermissionToChangeRole(
        authenticatedUser.getUsername(),
        userForEditRole.getUsername()
    );

    userForEditRole.setRole(Role.valueOf(newRole));
    userRepository.save(userForEditRole);
  }

  private void validatePermissionToChangeRole(String authUsername, String editableUsername) {
    if (authUsername.equals(editableUsername) || editableUsername.equals(MAIN_ADMIN_USERNAME))
      throw new UserValidationException("Forbidden to change the role of the selected user");
  }

  private boolean areEqualPasswords(String password, String repeatPassword) {
    return password.equals(repeatPassword);
  }

}
