package fr.utc.sr03.controller;


import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class WebController {

    @Resource
    private UserService userService;

    @GetMapping("/")
    public String homeAdmin(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("popupMessage", users);
        model.addAttribute("allUsers", users);
        return "admin/homeAdmin";
    }

    @GetMapping("/{warning}")
    public String homeAdmin(Model model, @PathVariable String warning) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("popupMessage", warning);
        model.addAttribute("allUsers", users);
        return "admin/homeAdmin";
    }

    @GetMapping("/test")
    public String test(){
        System.out.println(userService.checkPasswordFormat("test"));
        System.out.println(userService.checkPasswordFormat("etestT13"));
        System.out.println(userService.checkPasswordFormat("etes.tT13"));
        System.out.println(userService.checkPasswordFormat("et.T13"));
        System.out.println(userService.checkEmailFormat("this@ot.fr"));
        System.out.println(userService.checkEmailFormat(".this@ot.fr"));
        System.out.println(userService.checkEmailFormat("this.is.me@ot.fr"));
        System.out.println(userService.checkEmailFormat("this.is.me.@something.absurd"));
        System.out.println(userService.checkEmailFormat("this.is.me@something.com"));
        return "redirect:/admin/";
    }

    @GetMapping("/user/{idUser}")
    public User userAccount(@PathVariable int idUser) {
        return userService.getUserById(idUser);
    }

    @GetMapping("/adduser")
    public String formAddUser(){
        return "admin/formAddUser";
    }

    @PostMapping("/adduser")
    public String createUser(@ModelAttribute User user) {
        switch (userService.saveUser(user)){
            case -1:
                return "redirect:/admin/warning?=Mail_invalide";
            case -2:
                return "redirect:/admin/warning?=Password_invalide";
            default:
                return "redirect:/admin/";
        }
    }

    @PostMapping("/edituser")
    public String editUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam int idUser){
        userService.deleteUser(idUser);
        return "redirect:/admin/";
    }

}

