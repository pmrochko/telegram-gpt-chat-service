package com.mrochko.dev.telegramgptchatservice.data.mapper;

import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import com.mrochko.dev.telegramgptchatservice.data.entity.ChatLog;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatLogMapper {

  ChatLogMapper INSTANCE = Mappers.getMapper(ChatLogMapper.class);

  @Mapping(target = "id", ignore = true)
  ChatLog dtoToChatLogEntity(ChatLogDTO chatLogDTO);

  ChatLogDTO chatLogEntityToDto(ChatLog chatLog);

  List<ChatLogDTO> chatLogsListToDtoList(List<ChatLog> chatLogList);

}
