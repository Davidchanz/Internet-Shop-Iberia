package com.InternetShopIberia.validation;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto o, ConstraintValidatorContext constraintValidatorContext) {
        UserDto user = o;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
