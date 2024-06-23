package com.sample.music.annotation;

import com.sample.music.validation.StatusValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented // 元注解
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {StatusValidation.class}) // 指定提供校验规则的类
public @interface Status {
    // 提供校验失败后的提示信息
    String message() default "status参数的值只能是0或1";
    // 指定分组
    Class<?>[] groups() default {};
    // 负载 获取到State注释的附加信息
    Class<? extends Payload>[] payload() default {};
}