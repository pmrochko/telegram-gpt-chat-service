package com.mrochko.dev.telegramgptchatservice.service;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserDTO;
import com.mrochko.dev.telegramgptchatservice.data.dto.UserRegisterDTO;
import java.util.List;
import org.springframework.security.core.Authentication;

/**
 * @author Pavlo Mrochko
 */
public interface UserService {

  void registerUser(UserRegisterDTO userRegisterDTO);

  List<UserDTO> getAllUsersSortedById();

  void updateUserRole(Authentication auth, Long userId, String newRole);

}
