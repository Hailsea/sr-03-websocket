package fr.utc.sr03.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class WebController {

    @Resource
    private UserService userService;

    @GetMapping(value="/")
    public String root(){
        return "redirect:/login";

    @GetMapping("/home")
    public String homeAdmin(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("allUsers", users);
        return "admin/homeAdmin";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable int id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/profilUser";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login.html";

    @GetMapping("/adduser")
    public String formAddUser(){
        return "admin/formAddUser";
    }

    @PostMapping("/adduser")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/edituser/{id}")
    public String userAccount(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/formEditUser";
    }

    @PostMapping("/edituser/{id}")
    public String editUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/";
    @PostMapping(value="/login")
    public String loginRequest(Model model, @RequestParam String email, String password){
        User user=userService.getUserByEmailAddress(email);
        if (user!=null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            return "redirect:/admin/";
        }
        model.addAttribute("popupMessage","Mail ou mot de passe invalide.");
        return "login.html";
    }

    @PostMapping("/deleteuser")
    public void deleteUser(@RequestParam int id){
        userService.deleteUser(id);
    }
}

