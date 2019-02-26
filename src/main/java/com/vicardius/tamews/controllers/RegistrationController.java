package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Role;
import com.vicardius.tamews.models.User;
import com.vicardius.tamews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user) {
        user.setActive(true);
        user.setRoleUser(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

}