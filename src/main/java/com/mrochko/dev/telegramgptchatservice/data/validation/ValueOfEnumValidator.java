package com.mrochko.dev.telegramgptchatservice.data.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavlo Mrochko
 */
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Enum<?>> {
  private List<String> acceptedValues;

  @Override
  public void initialize(ValueOfEnum annotation) {
    acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(Enum<?> inputedEnum, ConstraintValidatorContext context) {
    return (inputedEnum != null) && (acceptedValues.contains(inputedEnum.name()));
  }

}
