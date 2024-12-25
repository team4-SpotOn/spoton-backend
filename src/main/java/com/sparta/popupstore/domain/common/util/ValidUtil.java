package com.sparta.popupstore.domain.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidUtil {

    public static boolean isNotNullAndEmpty(Object obj) {
        return obj != null && !obj.toString().isEmpty();
    }
}
