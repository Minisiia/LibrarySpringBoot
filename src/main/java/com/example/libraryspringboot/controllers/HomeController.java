package com.example.libraryspringboot.controllers;

import com.example.libraryspringboot.models.User;
import com.example.libraryspringboot.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping()
    public String home() {
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        authenticate(model);
        return "index";
    }

    public static void authenticate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails personDetails = (MyUserDetails) authentication.getPrincipal();
            User user = personDetails.getUser();
            model.addAttribute("currentRole", user.getRole());
            model.addAttribute("myUser", user);
    }
}
