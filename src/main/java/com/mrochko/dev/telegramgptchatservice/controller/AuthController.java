package com.mrochko.dev.telegramgptchatservice.controller;

import com.mrochko.dev.telegramgptchatservice.data.dto.UserRegisterDTO;
import com.mrochko.dev.telegramgptchatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pavlo Mrochko
 */
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  @GetMapping
  public String getRegistrationForm(Model model) {
    model.addAttribute("userRegisterDTO", new UserRegisterDTO());
    return "register";
  }

  @PostMapping
  public String registration(@ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO) {
    userService.registerUser(userRegisterDTO);
    return "redirect:/register?success";
  }

}
