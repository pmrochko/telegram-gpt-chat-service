package com.mrochko.dev.telegramgptchatservice.repository;

import com.mrochko.dev.telegramgptchatservice.data.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pavlo Mrochko
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByUsername(String username);

  boolean existsUserByUsername(String username);

}
