package com.sparta.popupstore.domain.common.validation;

import com.sparta.popupstore.domain.common.annotation.StartAndDateTimeCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;


@Slf4j
public class StartAndDateTimeCheckValidation implements ConstraintValidator<StartAndDateTimeCheck, Object> {
    private String message;
    private String startDateTime;
    private String endDateTime;

    @Override
    public void initialize(StartAndDateTimeCheck constraintAnnotation) {
        message = constraintAnnotation.message();
        startDateTime = constraintAnnotation.startDateTime();
        endDateTime = constraintAnnotation.endDateTime();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        LocalDateTime fieldStartTimeDate = this.getFieldValue(o, startDateTime);
        LocalDateTime fieldEndDateTime = this.getFieldValue(o, endDateTime);
        if(fieldStartTimeDate.isAfter(fieldEndDateTime) || fieldStartTimeDate.isBefore(LocalDateTime.now().plusDays(1))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(startDateTime)
                    .addConstraintViolation();
            valid = false;
        }
        if(fieldEndDateTime.isBefore(LocalDateTime.now())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("종료일은 현재 날짜보다 이후로 정하셔야합니다")
                    .addPropertyNode(endDateTime)
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }

    private LocalDateTime getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        try {
            Field dateField = clazz.getDeclaredField(fieldName);
            dateField.setAccessible(true);
            Object target = dateField.get(object);
            if (!(target instanceof LocalDateTime)) {
                throw new ClassCastException("casting exception");
            }
            return (LocalDateTime) target;
        } catch (NoSuchFieldException e) {
            log.info("NoSuchFieldException "+e.getMessage());
        } catch (IllegalAccessException e) {
            log.info("IllegalAccessException "+e.getMessage());
        }
        throw new IllegalArgumentException("startDateTime 과 endDateTime 이 존재하지 않습니다.");
    }
}
