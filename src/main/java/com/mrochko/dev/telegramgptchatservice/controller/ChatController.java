package com.mrochko.dev.telegramgptchatservice.controller;

import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import com.mrochko.dev.telegramgptchatservice.service.ChatLogService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Pavlo Mrochko
 */
@Controller
@RequestMapping("/admin/chats")
@RequiredArgsConstructor
public class ChatController {

  private final ChatLogService chatLogService;

  @GetMapping
  public String getAllChats(Model model) {
    Map<Long, List<ChatLogDTO>> chatLogsDtoMap = chatLogService.getAllChatLogsGroupedByChatId();
    model.addAttribute("chats", chatLogsDtoMap);
    return "admin/chats";
  }

  @GetMapping("/{chatId}/history")
  public String getChatHistoryById(Model model, @PathVariable Long chatId) {
    List<ChatLogDTO> chatLogs = chatLogService.getChatLogsByChatId(chatId);
    model.addAttribute("chatLogs", chatLogs);
    return "admin/chat-history";
  }

}
