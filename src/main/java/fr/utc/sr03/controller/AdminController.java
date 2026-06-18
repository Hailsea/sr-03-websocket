package fr.utc.sr03.controller;

import fr.utc.sr03.model.User;
import fr.utc.sr03.services.AuthService;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @Resource
    private AuthService authService;

    private boolean isAdminConnected(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user == null || !Boolean.TRUE.equals(user.getAdmin());
    }

    @GetMapping(value={"", "/"})
    public String homeAdmin(Model model, HttpSession session,
                            @RequestParam(value="status", defaultValue="all") String status) {
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        List<User> users = userService.getUsersByStatus(status);
        model.addAttribute("allUsers", users);
        model.addAttribute("status", status); // pour surligner le filtre actif dans la vue
        model.addAttribute("currentUser", session.getAttribute("user")); // admin connecte, affiche dans la barre d'outils
        return "admin/homeAdmin";
    }

    @GetMapping("/test")
    public String test(Model model, HttpSession session){
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        model.addAttribute("User",new User());
        model.addAttribute("popupMessage", "things");
        return "admin/formAddUser";
    }

    @GetMapping("/user/{idUser}")
    public String userAccount(Model model, HttpSession session, @PathVariable int idUser) {
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        User user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "admin/profilUser";
    }

    @GetMapping("/adduser")
    public String formAddUser(Model model, HttpSession session){
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        model.addAttribute("User",new User());
        model.addAttribute("popupMessage", "");
        return "admin/formAddUser";
    }

    @PostMapping("/saveuser")
    public String createUser(Model model, HttpSession session, @ModelAttribute User user,@RequestParam String edit, @RequestParam(required = false) String password_confirmation) {
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        int retour=0;
        if (edit.equals("false")){
            retour=userService.saveUser(user,password_confirmation);
        }
        else {
            retour = userService.updateUser(user);
        }
        switch (retour) {
            case -1:
                model.addAttribute("popupMessage", "Mail invalide");
                model.addAttribute("User", user);
                return edit.equals("false")? "/admin/formAddUser" : "/admin/formEditUser";
            case -2:
                model.addAttribute("popupMessage", "Mot de passe invalide : doit contenir au minimum une majuscule, une minuscule, un chiffre, un caractère spécial et 8 caractères minimum");
                model.addAttribute("User", user);
                return edit.equals("false")? "/admin/formAddUser" : "/admin/formEditUser";
            case -3:
                model.addAttribute("popupMessage", "Compte existant.");
                model.addAttribute("User", user);
                return edit.equals("false")? "/admin/formAddUser" : "/admin/formEditUser";
            case -4:
                model.addAttribute("popupMessage", "Les mots de passe ne correspondent pas.");
                model.addAttribute("User", user);
                return edit.equals("false")? "/admin/formAddUser" : "/admin/formEditUser";
            default:
                return "redirect:/admin/";
        }
    }

    @PostMapping("/edituser")
    public String editUser(@RequestParam int idUser, Model model, HttpSession session) {
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        User user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        model.addAttribute("popupMessage", "");
        return "admin/formEditUser";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam int idUser, HttpSession session, RedirectAttributes redirectAttributes){
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        boolean deleted = userService.deleteUser(idUser);
        if (!deleted) {
            redirectAttributes.addFlashAttribute("popupMessage", "Suppression impossible : il doit rester au moins un administrateur.");
        }
        return "redirect:/admin/";
    }

    @PostMapping("/toggleactive")
    public String toggleActive(@RequestParam int idUser, HttpSession session, RedirectAttributes redirectAttributes){
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        User current = (User) session.getAttribute("user");
        boolean changed = userService.toggleActive(idUser, current.getId());
        if (!changed) {
            redirectAttributes.addFlashAttribute("popupMessage", "Vous ne pouvez pas désactiver votre propre compte.");
        }
        return "redirect:/admin/";
    }

    @PostMapping("/resetpassword")
    public String resetUserPassword(@RequestParam String email, RedirectAttributes redirectAttributes, HttpSession session) {
        if (isAdminConnected(session)) {
            return "redirect:/login";
        }
        authService.resetPassword(email);
        redirectAttributes.addFlashAttribute("popupMessage", "Mot de passe réinitialisé pour " + email);
        return "redirect:/admin/";
    }
}
