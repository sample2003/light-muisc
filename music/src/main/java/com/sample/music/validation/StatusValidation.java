package com.sample.music.validation;

import com.sample.music.annotation.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidation implements ConstraintValidator<Status, Integer> {
    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        // 提供校验规则 value：要校验的数据
        if (status == null){
            return false;
        }
        return status == 1 || status == 0;
    }
}
