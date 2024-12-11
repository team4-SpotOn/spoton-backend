package com.sparta.popupstore.domain.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IsValidNullAndEmpty {

    public static boolean isValidNullAndEmpty(Object obj) {
        return obj != null && !obj.toString().isEmpty();
    }
}
