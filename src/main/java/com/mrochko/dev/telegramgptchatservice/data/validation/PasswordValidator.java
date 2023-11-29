package com.mrochko.dev.telegramgptchatservice.data.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Pavlo Mrochko
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, CharSequence> {

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    return (value != null) && (value.toString().matches(regex));
  }

}
