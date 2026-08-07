package com.example.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.dto.RegisterDTO;
import com.example.laptopshop.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password and confirmPassword match
        if (user.getPassword() == null || user.getConfirmPassword() == null
                || !user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Mật khẩu nhập lại không chính xác")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            valid = false;
        }

        // Check if email already exists
        if (user.getEmail() != null && this.userService.checkEmailExists(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
