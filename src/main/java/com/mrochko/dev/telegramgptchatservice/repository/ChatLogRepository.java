package com.mrochko.dev.telegramgptchatservice.repository;

import com.mrochko.dev.telegramgptchatservice.data.entity.ChatLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

  boolean existsChatLogByChatId(Long chatId);

  List<ChatLog> findChatLogsByChatId(Long chatId);

}
