package fr.utc.sr03.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@Controller
public class WebController {

    @Resource
    private UserService userService;

    @GetMapping(value="/")
    public String root(){
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login.html";
    }

    @PostMapping(value="/login")
    public String loginRequest(Model model, @RequestParam String email, String password){
        User user=userService.getUserByEmailAddress(email);
        if (user!=null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            return "redirect:/admin/";
        }
        model.addAttribute("popupMessage","Mail ou mot de passe invalide.");
        return "login.html";
    }
}

