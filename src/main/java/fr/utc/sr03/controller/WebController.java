package fr.utc.sr03.controller;


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

    @GetMapping("/")
    public String homeAdmin(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("allUsers", users);
        return "admin/homeAdmin";
    }

    @GetMapping("/user/{idUser}")
    public User userAccount(@PathVariable int idUser) {
        return userService.getUserById(idUser);
    }

    @GetMapping("/adduser")
    public String formAddUser(){
        return "formAddUser";
    }

    @PostMapping("/adduser")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/edituser")
    public void editUser(@ModelAttribute User user) {
        userService.saveUser(user);
    }

    @PostMapping("/deleteuser")
    public void deleteUser(@RequestParam int id){
        userService.deleteUser(id);
    }

}

