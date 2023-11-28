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
    String firstname = update.getMessage().getChat().getFirstName();
    String requestText = update.getMessage().getText();
    String chatId = String.valueOf(update.getMessage().getChatId());

    if (requestText.equals("/start")) {
      String startMessage =
          "Hi, " + firstname + ", nice to meet you!" + "\n" +
          "Enter the message to send to ChatGPT";
      sendMessageToChat(chatId, startMessage);
    } else {
      SendMessage sendMessage = new SendMessage();
      pong(sendMessage, chatId);
      String gptAnswer = askGpt(sendMessage, chatId, requestText);

      saveChatToDBLogs(firstname, chatId, requestText, gptAnswer);
    }

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

  private String askGpt(SendMessage sendMessage, String chatId, String text) {
    String gptResponse = chatGptService.askChatGptText(text);
    try {
      sendMessage.setChatId(chatId);
      sendMessage.setText("GPT answer: " + gptResponse);
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
    return gptResponse;
  }

  private void pong(SendMessage sendMessage, String chatId) {
    sendMessage.setText("I'm sending your message to ChatGPT, please wait..");
    try {
      sendMessage.setChatId(chatId);
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private void saveChatToDBLogs(String userFirstname, String chatId, String requestText, String gptAnswer) {

    ChatLogDTO chatLogDTO = ChatLogDTO.builder()
        .userFirstname(userFirstname)
        .chatId(Long.parseLong(chatId))
        .askMessage(requestText)
        .answerMessage(gptAnswer)
        .answerType(AnswerType.CHATGPT)
        .timestamp(LocalDateTime.now())
        .build();

    chatLogService.createChatLog(chatLogDTO);
  }

}
