package fr.utc.sr03.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.JakartaEmail;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Resource
    private UserService userService;
    private JakartaEmail jakartaEmail;

    @GetMapping(value="/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String login() {
        return "login";
    }

    @GetMapping(value="/resetPassword")
    public String resetPasswordForm() {
        return "passwordReset";
    }

    @PostMapping(value="/resetPassword")
    public String resetPassword(@RequestParam String email) {
        User user_to_reset=userService.getUserByEmailAddress(email);
        if (user_to_reset!=null){
            user_to_reset.setPassword("a");
            userService.saveUser(user_to_reset);
            jakartaEmail.sendMail("chenlaur@etu.utc.fr","chenlaur@etu.utc.fr","smtp1.utc.fr","DO NOT REPLY : TEST","COUCOU");
        }
        return "login";
    }
}

