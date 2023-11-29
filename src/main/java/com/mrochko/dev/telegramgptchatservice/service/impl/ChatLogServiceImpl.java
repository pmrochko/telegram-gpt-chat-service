package com.mrochko.dev.telegramgptchatservice.service.impl;

import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import com.mrochko.dev.telegramgptchatservice.data.entity.ChatLog;
import com.mrochko.dev.telegramgptchatservice.data.mapper.ChatLogMapper;
import com.mrochko.dev.telegramgptchatservice.exceptions.chat.ChatNotFoundException;
import com.mrochko.dev.telegramgptchatservice.repository.ChatLogRepository;
import com.mrochko.dev.telegramgptchatservice.service.ChatLogService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Pavlo Mrochko
 */
@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {

  private final static String CHAT_NOT_FOUND_MESSAGE = "Chat was not found by the specified ID";

  private final ChatLogRepository chatLogRepository;

  @Override
  public void createChatLog(ChatLogDTO chatLogDTO) {
    chatLogRepository.save(ChatLogMapper.INSTANCE.dtoToChatLogEntity(chatLogDTO));
  }

  @Override
  public Map<Long, List<ChatLogDTO>> getAllChatLogsGroupedByChatId() {

    List<ChatLog> allLogs = chatLogRepository.findAll();

    Map<Long, List<ChatLogDTO>> chatLogListByChatIdMap = allLogs.stream()
        .collect(Collectors.groupingBy(
            ChatLog::getChatId,
            Collectors.mapping(
                ChatLogMapper.INSTANCE::chatLogEntityToDto,
                Collectors.toList())));

    return chatLogListByChatIdMap;
  }

  @Override
  public List<ChatLogDTO> getChatLogsByChatId(Long chatId) {
    if (!chatLogRepository.existsChatLogByChatId(chatId))
      throw new ChatNotFoundException(CHAT_NOT_FOUND_MESSAGE);

    return ChatLogMapper.INSTANCE.chatLogsListToDtoList(
        chatLogRepository.findAllByChatIdOrderByTimestampAsc(chatId));
  }

  @Override
  public String getUserFirstnameByChatId(Long chatId) {
    return chatLogRepository.findFirstByChatId(chatId)
        .orElseThrow(() -> new ChatNotFoundException(CHAT_NOT_FOUND_MESSAGE))
        .getUserFirstname();
  }

}
