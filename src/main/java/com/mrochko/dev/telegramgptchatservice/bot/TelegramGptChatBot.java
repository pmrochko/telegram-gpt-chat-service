package com.mrochko.dev.telegramgptchatservice.bot;

import com.mrochko.dev.telegramgptchatservice.config.TelegramBotConfig;
import com.mrochko.dev.telegramgptchatservice.data.dto.ChatLogDTO;
import com.mrochko.dev.telegramgptchatservice.data.enumeration.AnswerType;
import com.mrochko.dev.telegramgptchatservice.service.ChatGptService;
import com.mrochko.dev.telegramgptchatservice.service.ChatLogService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Pavlo Mrochko
 */
@Component
@RequiredArgsConstructor
public class TelegramGptChatBot extends TelegramLongPollingBot {

  private final ChatGptService chatGptService;
  private final ChatLogService chatLogService;
  private final TelegramBotConfig botConfig;

  @Override
  public String getBotUsername() {
    return botConfig.getBotName();
  }

  @Override
  public String getBotToken() {
    return botConfig.getBotToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (!isValidUpdate(update)) return;

    Message message = update.getMessage();
    Chat chatInfo = message.getChat();

    String requestText = message.getText();

    String firstname = chatInfo.getFirstName();
    String name = !firstname.isBlank() ? firstname : chatInfo.getUserName();

    String chatId = message.getChatId().toString();
    String answerText;

    if (requestText.equals("/start")) {
      answerText = "Hi, " + name + ", nice to meet you!" + "\n" +
                   "Enter the message to send to ChatGPT";
      sendMessageToChat(chatId, answerText);
    } else if (requestText.startsWith("/")) {
      answerText = "Sorry, I don't know any command yet. " + "\n" +
                   "Try enter the message";
      sendMessageToChat(chatId, answerText);
    } else {
      pong(chatId);
      answerText = askGpt(chatId, requestText);
    }

    saveChatLogToDB(name, Long.parseLong(chatId), requestText, answerText, AnswerType.CHATGPT);
  }

  private boolean isValidUpdate(Update update) {
    return (update.hasMessage() &&
            update.getMessage().hasText());
  }

  private void sendMessageToChat(String chatId, String textToSend) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(textToSend);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void sendAdminMessage(Long chatId, String adminMessage) {
    sendMessageToChat(chatId.toString(), "[ADMINISTRATOR MESSAGE] \n" + adminMessage);
    String firstname = chatLogService.getUserFirstnameByChatId(chatId);
    saveChatLogToDB(firstname, chatId, "", adminMessage, AnswerType.ADMIN);
  }

  private String askGpt(String chatId, String text) {
    String gptResponse = chatGptService.askChatGptText(text);
    String answerMessage = "[GPT ANSWER] \n" + gptResponse;
    sendMessageToChat(chatId, answerMessage);
    return gptResponse;
  }

  private void pong(String chatId) {
    String pongMessage = "I'm sending your message to ChatGPT, please wait..";
    sendMessageToChat(chatId, pongMessage);
  }

  private void saveChatLogToDB(
      String userFirstname,
      Long chatId,
      String requestText,
      String answerText,
      AnswerType answerType
  ) {

    ChatLogDTO chatLogDTO = ChatLogDTO.builder()
        .userFirstname(userFirstname)
        .chatId(chatId)
        .askMessage(requestText)
        .answerMessage(answerText)
        .answerType(answerType)
        .timestamp(LocalDateTime.now())
        .build();

    chatLogService.createChatLog(chatLogDTO);
  }

}
