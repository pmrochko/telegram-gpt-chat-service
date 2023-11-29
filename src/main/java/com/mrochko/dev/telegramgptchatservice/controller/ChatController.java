package com.mrochko.dev.telegramgptchatservice.controller;

import com.mrochko.dev.telegramgptchatservice.bot.TelegramGptChatBot;
import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import com.mrochko.dev.telegramgptchatservice.service.ChatLogService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pavlo Mrochko
 */
@Controller
@RequestMapping("/admin/chats")
@RequiredArgsConstructor
public class ChatController {

  private final ChatLogService chatLogService;
  private final TelegramGptChatBot chatBot;

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

  @PostMapping("/{chatId}/send-admin-message")
  public String sentAdminMessage(
      HttpServletRequest request,
      @PathVariable Long chatId,
      @RequestParam String adminMessage
  ) {
    chatBot.sendAdminMessage(chatId, adminMessage);
    String refererPage = request.getHeader("Referer");
    return "redirect:" + refererPage;
  }

}
