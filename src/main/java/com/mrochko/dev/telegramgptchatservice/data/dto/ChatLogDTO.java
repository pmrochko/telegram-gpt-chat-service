package com.mrochko.dev.telegramgptchatservice.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrochko.dev.telegramgptchatservice.data.enumeration.AnswerType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * @author Pavlo Mrochko
 * DTO for {@link com.mrochko.dev.telegramgptchatservice.data.entity.ChatLog}
 */
@Data
@Builder
public class ChatLogDTO {

  Long id;
  String userFirstname;
  Long chatId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime timestamp;
  String askMessage;
  String answerMessage;
  AnswerType answerType;

}
