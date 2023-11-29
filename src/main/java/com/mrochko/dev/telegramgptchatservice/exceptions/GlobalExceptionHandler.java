package com.mrochko.dev.telegramgptchatservice.exceptions;

import com.mrochko.dev.telegramgptchatservice.exceptions.chat.ChatNotFoundException;
import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserNotFoundException;
import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Pavlo Mrochko
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  public static final String REFERER_URL_HEADER_NAME = "Referer";
  public static final String ORIGIN_URL_HEADER_NAME = "Origin";

  @ExceptionHandler(UserValidationException.class)
  public String handleUserValidationException(
      HttpServletRequest request,
      RedirectAttributes attributes,
      UserValidationException ex
  ) {
    String viewPage = extractUrlViewPage(request);
    addErrorMessageToAttributes(attributes, ex.getMessage());
    return "redirect:" + viewPage;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public String handleUserNotFoundException(
      HttpServletRequest request,
      RedirectAttributes attributes,
      UserNotFoundException ex
  ) {
    String viewPage = extractUrlViewPage(request);
    addErrorMessageToAttributes(attributes, ex.getMessage());
    return "redirect:" + viewPage;
  }

  @ExceptionHandler(ChatNotFoundException.class)
  public String handleChatNotFoundException(
      RedirectAttributes attributes,
      ChatNotFoundException ex
  ) {
    addErrorMessageToAttributes(attributes, ex.getMessage());
    return "redirect:/admin/chats";
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String handleMethodArgumentNotValidException(
      HttpServletRequest request,
      RedirectAttributes attributes,
      MethodArgumentNotValidException ex
  ) {
    String viewPage = extractUrlViewPage(request);
    String joinedListOfValidationErrors = ex.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining());

    addErrorMessageToAttributes(attributes, joinedListOfValidationErrors);

    return "redirect:" + viewPage;
  }

  private void addErrorMessageToAttributes(RedirectAttributes attributes, String errorMessage) {
    attributes.addFlashAttribute("error", errorMessage);
  }

  private String extractUrlViewPage(HttpServletRequest request) {
    String fullUrl = request.getHeader(REFERER_URL_HEADER_NAME);
    String hostUrl = request.getHeader(ORIGIN_URL_HEADER_NAME);

    return (fullUrl.startsWith(hostUrl)) ?
        fullUrl.substring(hostUrl.length()) : fullUrl;
  }

}
