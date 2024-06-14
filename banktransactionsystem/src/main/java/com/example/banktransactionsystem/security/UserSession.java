package com.example.banktransactionsystem.security;

import com.example.banktransactionsystem.entity.User;
import com.example.banktransactionsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSession {
    private UserService userService;

    public int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userService.getUserByUsername(authentication.getName());
            return user.getId();
        }
        return SecurityConstants.NOT_FOUND;
    }
}
