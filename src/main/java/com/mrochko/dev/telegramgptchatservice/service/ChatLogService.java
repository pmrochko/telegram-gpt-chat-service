package com.mrochko.dev.telegramgptchatservice.service;

import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import java.util.List;
import java.util.Map;

/**
 * @author Pavlo Mrochko
 */
public interface ChatLogService {

  void createChatLog(ChatLogDTO chatLogDTO);

  Map<Long, List<ChatLogDTO>> getAllChatLogsGroupedByChatId();

  List<ChatLogDTO> getChatLogsByChatId(Long chatId);

  String getUserFirstnameByChatId(Long chatId);

}
