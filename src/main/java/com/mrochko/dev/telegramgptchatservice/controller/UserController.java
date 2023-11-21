package com.mrochko.dev.telegramgptchatservice.controller;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserDTO;
import com.mrochko.dev.telegramgptchatservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pavlo Mrochko
 */
@Controller
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public String getAllUsers(Model model) {
    List<UserDTO> userDtoList = userService.getAllUsersSortedById();
    model.addAttribute("users", userDtoList);
    return "admin/users";
  }

  @PatchMapping("/{userId}/role")
  public String updateUserRole(Authentication auth, @PathVariable Long userId, @RequestParam String newRole) {
    userService.updateUserRole(auth, userId, newRole);
    return "redirect:/admin/users";
  }

}
