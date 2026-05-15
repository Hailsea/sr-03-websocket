package fr.utc.sr03.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @PostMapping(value="/login")
    public String loginRequest(Model model, HttpSession session, @RequestParam String email, String password){
        User user=userService.getUserByEmailAddress(email);
        if (user!=null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
            //session.setAttribute("user", user); //TODO session id role
            //System.out.println(session.getAttribute("user"));
            if (user.getAdmin()) {
                return "redirect:/admin/home";
            }
            return "redirect:/user/home";
        }
        model.addAttribute("popupMessage","Mail ou mot de passe invalide.");
        return "login";
    }

    @GetMapping("/")
    public String homeAdmin(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("popupMessage", "");
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
        System.out.println(userService.getUserByEmailAddress("not existing"));
        System.out.println(userService.getUserByEmailAddress("james.jame@gmail.com"));
        return "redirect:/admin/";
    }

    @GetMapping("/user/{idUser}")
    public String userAccount(Model model, @PathVariable int idUser) {
        User user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "admin/profilUser";
    }

    @GetMapping("/adduser")
    public String formAddUser(Model model){
        model.addAttribute("User",new User());
        return "admin/formAddUser";
    }

    @PostMapping("/saveuser")
    public String createUser(Model model, @ModelAttribute User user, String editPassword) {
        int retour=0;
        if (editPassword.equals("true")){
            retour=userService.saveUser(user);
        }
        else{
            retour=userService.updateUser(user);
        }
        System.out.println(retour);
        System.out.println(editPassword);
        switch (retour) {
            case -1:
                model.addAttribute("popupMessage", "Mail invalide");
                model.addAttribute("User", user);
                return "/admin/formAddUser";
            case -2:
                model.addAttribute("popupMessage", "Mot de passe invalide : doit contenir au minimum une majuscule, une minuscule, un chiffre, un caractère spécial et 8 caractères minimum");
                model.addAttribute("User", user);
                return "/admin/formAddUser";
            default:
                return "redirect:/admin/";
        }
    }

    @PostMapping("/edituser")
    public String editUser(@RequestParam int idUser, Model model) {
        User user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "admin/formEditUser";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam int idUser){
        userService.deleteUser(idUser);
        return "redirect:/admin/";
    }
}

