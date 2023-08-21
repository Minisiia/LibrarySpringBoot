package com.example.libraryspringboot.util;

import com.example.libraryspringboot.models.User;
import com.example.libraryspringboot.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MyUserValidator implements Validator {

    private final MyUserDetailsService myUserDetailsService;
@Autowired
    public MyUserValidator(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        try {
            myUserDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // Все нормально, користувача не знайдено
        }

        errors.rejectValue("username", "", "Людина з таким іменем користувача вже існує");
    }
}
