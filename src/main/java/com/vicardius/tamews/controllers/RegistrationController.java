package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Role;
import com.vicardius.tamews.models.User;
import com.vicardius.tamews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerUser(User user/*, Model model*/) {
//        User userFromDB = userRepository.findByEmailUser(user.getEmailUser());
//        if (userFromDB != null) {
//            model.addAttribute("message", "User exists!");
//            return "registration";
//        }
        user.setActive(true);
        user.setRoleUser(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

}