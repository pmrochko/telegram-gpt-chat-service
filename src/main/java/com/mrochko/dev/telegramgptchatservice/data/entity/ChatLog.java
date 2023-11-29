package com.mrochko.dev.telegramgptchatservice.data.entity;

import com.mrochko.dev.telegramgptchatservice.data.enumeration.AnswerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Pavlo Mrochko
 */
@Getter
@Setter
@Entity
@Table(name = "chat_logs")
public class ChatLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "log_id", nullable = false)
  private Long id;

  @Column(name = "user_firstname", nullable = false)
  private String userFirstname;

  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @Column(name = "ask_message", nullable = false, length = Integer.MAX_VALUE)
  private String askMessage;

  @Column(name = "answer_message", nullable = false, length = Integer.MAX_VALUE)
  private String answerMessage;

  @Enumerated(EnumType.STRING)
  @Column(name = "answer_type", updatable = false, nullable = false, length = 20)
  private AnswerType answerType;

}
