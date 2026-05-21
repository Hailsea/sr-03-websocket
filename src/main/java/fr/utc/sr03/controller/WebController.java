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

    @GetMapping(value="/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String login(Model model) {
        model.addAttribute("popupMessage","");
        return "login";
    }

    @GetMapping(value="/resetPassword")
    public String resetPasswordForm() {
        return "passwordReset";
    }

    @PostMapping(value="/resetPassword")
    public String resetPassword(@RequestParam String email) {
        userService.resetPassword(email);
        return "login";
    }
}

