package com.anuj.shoppify.util;

import com.anuj.shoppify.auth.dto.LoggedInUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUserUtil {

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof LoggedInUser) {
            return ((LoggedInUser) principal).getUserId();
        }

        throw new RuntimeException("User not authenticated or not of type LoggedInUser");
    }
}
