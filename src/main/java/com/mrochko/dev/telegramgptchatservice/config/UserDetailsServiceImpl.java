package com.mrochko.dev.telegramgptchatservice.config;

import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import com.mrochko.dev.telegramgptchatservice.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Pavlo Mrochko
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userRepository.findUserByUsername(username);
    return user.map(this::toUserDetails)
        .filter(details -> username.equals(details.getUsername()))
        .orElseThrow(() -> new UsernameNotFoundException("Wrong provided username"));
  }

  private UserDetails toUserDetails(User user) {
    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole().name()).build();
  }

}
