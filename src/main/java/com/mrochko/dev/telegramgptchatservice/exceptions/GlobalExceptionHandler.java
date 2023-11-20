package com.mrochko.dev.telegramgptchatservice.exceptions;

import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserNotFoundException;
import com.mrochko.dev.telegramgptchatservice.exceptions.user.UserValidationException;
import jakarta.servlet.http.HttpServletRequest;
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
    addErrorMessageToAttributes(attributes, ex);
    return "redirect:" + viewPage;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public String handleUserNotFoundException(
      HttpServletRequest request,
      RedirectAttributes attributes,
      UserNotFoundException ex
  ) {
    String viewPage = extractUrlViewPage(request);
    addErrorMessageToAttributes(attributes, ex);
    return "redirect:" + viewPage;
  }

  private void addErrorMessageToAttributes(RedirectAttributes attributes, RuntimeException ex) {
    attributes.addFlashAttribute("error", ex.getMessage());
  }

  private String extractUrlViewPage(HttpServletRequest request) {
    String fullUrl = request.getHeader(REFERER_URL_HEADER_NAME);
    String baseUrl = request.getHeader(ORIGIN_URL_HEADER_NAME);

    return (fullUrl.startsWith(baseUrl)) ?
        fullUrl.substring(baseUrl.length()) : fullUrl;
  }

}
