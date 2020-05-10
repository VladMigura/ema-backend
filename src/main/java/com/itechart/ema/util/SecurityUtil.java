package com.itechart.ema.util;

import com.itechart.ema.entity.enums.UserRoleEntity;
import com.itechart.ema.security.impl.UserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@UtilityClass
public class SecurityUtil {

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl;
    }

    public static UUID getUserId() {
        var userDetails = getUserDetails();
        return userDetails.getUserId();
    }

    public static UserRoleEntity getUserRole() {
        var userDetails = getUserDetails();
        return userDetails.getRole();
    }

    public static UserDetailsImpl getUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails();
    }

}
