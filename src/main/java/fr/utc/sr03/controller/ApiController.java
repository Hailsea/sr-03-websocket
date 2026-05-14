package fr.utc.sr03.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.JakartaEmail;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/create")
    public void create(){
        User user = new User();
        user.setFirstname("Cédric");
        user.setLastname("Martinet");
        user.setEmail("cedric.martinet@utc.fr");
        userService.saveUser(user);
    }

    @GetMapping(value = "/liste")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/oneUser/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping(value = "/testmail")
    public void testmail(){
        //Test envoi Mail
        JakartaEmail jakartaEmail = new JakartaEmail();
        jakartaEmail.sendMail();
    }

    // connection User



}
