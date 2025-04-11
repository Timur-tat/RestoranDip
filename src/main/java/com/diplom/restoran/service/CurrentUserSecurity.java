package com.diplom.restoran.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class CurrentUserSecurity {
    protected String getCurrentUsername() {
        // Получаем аутентификацию из SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString(); // Если principal не UserDetails, возвращаем строковое представление
        }
    }
}
