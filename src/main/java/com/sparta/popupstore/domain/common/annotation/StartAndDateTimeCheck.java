package com.sparta.popupstore.domain.common.annotation;

import com.sparta.popupstore.domain.common.validation.StartAndDateTimeCheckValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartAndDateTimeCheckValidation.class)
public @interface StartAndDateTimeCheck {
    String message() default "시작일은 종료일보다 빨라야합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String startDateTime();
    String endDateTime();
}
