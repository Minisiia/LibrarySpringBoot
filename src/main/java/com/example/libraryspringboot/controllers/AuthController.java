package com.example.libraryspringboot.controllers;

import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.models.User;
import com.example.libraryspringboot.services.RegistrationService;
import com.example.libraryspringboot.services.impl.PersonServiceImpl;
import com.example.libraryspringboot.validators.MyUserValidator;
import com.example.libraryspringboot.validators.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final MyUserValidator myUserValidator;

    private final PersonValidator personValidator;

    private  final PersonServiceImpl personService;

    @Autowired
    public AuthController(RegistrationService registrationService, MyUserValidator myUserValidator, PersonValidator personValidator, PersonServiceImpl personService) {
        this.registrationService = registrationService;
        this.myUserValidator = myUserValidator;
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user,
                                   @ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult userBindingResult,
                                      @ModelAttribute("person") @Valid Person person,
                                      BindingResult personBindingResult) {
        myUserValidator.validate(user, userBindingResult);
        personValidator.validate(person, personBindingResult);
        if (userBindingResult.hasErrors() || personBindingResult.hasErrors()) {
            return "/auth/registration";
        }
        personService.save(person);
        user.setPerson(person);
        registrationService.register(user);
        return "redirect:/auth/login";
    }
}
