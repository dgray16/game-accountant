package com.avid.web.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.BooleanUtils;

import java.util.function.Function;

@UtilityClass
public class PermissionChecker {

    public static void check(@NonNull final Boolean securityCheck, @NonNull final String accessDeniedMessage) {
        if (BooleanUtils.isFalse(securityCheck)) {
            throw new SecurityException(accessDeniedMessage);
        }
    }

    public static void check(@NonNull final Boolean securityCheck,
                             @NonNull final String accessDeniedMessage,
                             @NonNull final Function<String, ? extends RuntimeException> function) {
        RuntimeException ex = function.apply(accessDeniedMessage);
        if (BooleanUtils.isFalse(securityCheck)) {
            throw ex;
        }
    }

}
