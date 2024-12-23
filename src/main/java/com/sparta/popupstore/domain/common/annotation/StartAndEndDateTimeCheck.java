package com.sparta.popupstore.domain.common.annotation;

import com.sparta.popupstore.domain.common.validation.StartAndEndDateTimeCheckValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartAndEndDateTimeCheckValidation.class)
public @interface StartAndEndDateTimeCheck {
    String message() default "시작일은 종료일보다 빨라야하며, 현재 시간 보다 최소 24시간 뒤로 설정하셔야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String startDateTime();
    String endDateTime();
}
