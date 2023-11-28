package com.mrochko.dev.telegramgptchatservice.service.impl;

import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.AUTHORIZATION;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.CONTENT;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.FREQUENCY_PENALTY;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.MAX_TOKENS;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.MESSAGES;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.MODEL;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.MessagesRole;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.PRESENCE_PENALTY;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.ROLE;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.TEMPERATURE;
import static com.mrochko.dev.telegramgptchatservice.data.constants.GptRequestConstants.TOP_P;

import com.mrochko.dev.telegramgptchatservice.service.ChatGptService;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptServiceImpl implements ChatGptService {

  public static final String DEFAULT_SYSTEM_MESSAGE = "You are a Telegram online chat bot";
  public static final String CHOICES = "choices";
  public static final String MESSAGE = "message";

  @Value("${telegram-bot-api.text.temperature}")
  Double temperature;
  @Value("${telegram-bot-api.text.model}")
  String textModel;
  @Value("${telegram-bot-api.text.top-p}")
  Double topP;
  @Value("${telegram-bot-api.text.frequency-penalty}")
  Double freqPenalty;
  @Value("${telegram-bot-api.text.presence-penalty}")
  Double presPenalty;
  @Value("${telegram-bot-api.text.max-tokens}")
  Integer maxTokens;
  @Value("${chat-gpt-api.token}")
  String apiToken;
  @Value("${chat-gpt-api.url.completions}")
  String urlCompletions;

  @Override
  public String askChatGptText(String message) {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = setHeaders();
    HttpEntity<String> requestEntity = getStringHttpEntity(message, headers);

    URI chatGptUrl = URI.create(urlCompletions);
    ResponseEntity<String> responseEntity = restTemplate.
        postForEntity(chatGptUrl, requestEntity, String.class);

    JSONObject responseJson = new JSONObject(responseEntity.getBody());
    JSONArray choices = (JSONArray) responseJson.get(CHOICES);

    JSONObject firstChoice = (JSONObject) choices.get(0);
    JSONObject jsonMessage = (JSONObject) firstChoice.get(MESSAGE);
    return jsonMessage.get(CONTENT).toString();
  }

  private HttpEntity<String> getStringHttpEntity(String message, HttpHeaders headers) {
    Map<String, String> systemMessage = Map.of(
        ROLE, MessagesRole.SYSTEM.getValue(),
        CONTENT, DEFAULT_SYSTEM_MESSAGE
    );
    Map<String, String> userMessage = Map.of(
        ROLE, MessagesRole.USER.getValue(),
        CONTENT, message
    );

    JSONObject request = new JSONObject();
    request.put(MODEL, textModel);
    request.put(MESSAGES, Arrays.asList(systemMessage, userMessage));
    request.put(TEMPERATURE, temperature);
    request.put(MAX_TOKENS, maxTokens);
    request.put(TOP_P, topP);
    request.put(FREQUENCY_PENALTY, freqPenalty);
    request.put(PRESENCE_PENALTY, presPenalty);

    return new HttpEntity<>(request.toString(), headers);
  }

  private HttpHeaders setHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add(AUTHORIZATION, apiToken);
    return headers;
  }

}
