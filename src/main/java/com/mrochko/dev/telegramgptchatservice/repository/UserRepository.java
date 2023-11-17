package com.mrochko.dev.telegramgptchatservice.repository;

import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavlo Mrochko
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserByUsername(String username);

}
