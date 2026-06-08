package fr.utc.sr03.controller;

import fr.utc.sr03.dto.LoginDTO;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Resource
    private AuthService authService;

    @GetMapping(value="/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String login(Model model) {
        model.addAttribute("popupMessage","");
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(Model model, HttpSession session, @ModelAttribute LoginDTO login) {
        try {
            User user = authService.authenticate(login.getEmail(), login.getPassword());
            if (user.getAdmin()) {
                session.setAttribute("user", user);
                return "redirect:/admin";
            }
            return "redirect:http://localhost:5173";
        } catch (Exception e) {
            model.addAttribute("popupMessage", e.getMessage());
            return "login";
        }
    }

    @GetMapping(value="/resetPassword")
    public String resetPasswordForm() {
        return "passwordReset";
    }

    @PostMapping(value="/resetPassword")
    public String resetPassword(@RequestParam String email) {
        authService.resetPassword(email);
        return "login";
    }
}