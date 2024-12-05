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
        constraintValidatorContext.disableDefaultConstraintViolation(); // 컨텍스트의 기본 메세지를 삭제 / 기본메세지를 삭제하지 않으면 기본 메세지 뒤에 우리가 지정한 메세지가 붙게된다.
        LocalDateTime fieldStartTimeDate = this.getFieldValue(o, startDateTime);
        LocalDateTime fieldEndDateTime = this.getFieldValue(o, endDateTime);
        if(fieldStartTimeDate.isAfter(fieldEndDateTime) || fieldStartTimeDate.isBefore(LocalDateTime.now().plusDays(1))) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message) // 검증 실패시 보여줄 메세지 넣어줌
                    .addPropertyNode(startDateTime)// 이 메서드에서 검증한 대상 필드 -> "startDateTime"
                    .addConstraintViolation(); //구체적인 에러 메시지와 검증한 Node Key값을 넘겨준다. 해당 Node는 Errors 객체의 errors 리스트에 저장될 원소의 field 속성에 바인딩
            valid = false;
        }
        if(fieldEndDateTime.isBefore(LocalDateTime.now())) {
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
            Field dateField = clazz.getDeclaredField(fieldName); // 해당 이름의 필드를 가져옴
            dateField.setAccessible(true); // private 접근 제한자 필드에 접근하기 위해서는 true 로 설정 필요. 하지 않으면 접근제한자 필드에 접근을 못함
            Object target = dateField.get(object); // get 을 이용해서 필드의 값을 가져옴.
            if (!(target instanceof LocalDateTime)) { // 값의 타입을 비교 후 LocalDateTime 이 아니라면 예외 던짐
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
