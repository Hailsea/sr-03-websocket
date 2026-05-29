package fr.utc.sr03.controller;

import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/test")
    public String test(){
        return "user/chat";
    }
}

