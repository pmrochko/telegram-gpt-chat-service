package com.mrochko.dev.telegramgptchatservice.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mrochko.dev.telegramgptchatservice.data.enumeration.AnswerType;
import com.mrochko.dev.telegramgptchatservice.data.validation.ValueOfEnum;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link com.mrochko.dev.telegramgptchatservice.data.entity.ChatLog}
 * @author Pavlo Mrochko
 */
@Data
@Builder
public class ChatLogDTO {

  @JsonProperty(access = Access.READ_ONLY)
  Long id;

  String userFirstname;

  Long chatId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime timestamp;

  String askMessage;

  String answerMessage;

  @ValueOfEnum(enumClass = AnswerType.class)
  AnswerType answerType;

}
