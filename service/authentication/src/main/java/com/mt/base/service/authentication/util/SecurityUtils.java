package com.mt.base.service.authentication.util;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class SecurityUtils {
    public static UserDetails getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("登录状态已过期");
        }
        return userDetails;
    }

    public static String getUsername() {
        UserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }
}
