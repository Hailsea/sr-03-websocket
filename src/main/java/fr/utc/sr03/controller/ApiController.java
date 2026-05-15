package fr.utc.sr03.controller;


import fr.utc.sr03.model.Channel;
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

    /*@GetMapping(value = "/liste")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }*/

    /*@GetMapping(value = "/oneUser/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }*/

    @GetMapping("/user")
    public User getUser(@RequestParam(value = "id") int idUser) {
        User u = userService.getUserById(idUser);
        u.setPassword("*");
        return u;
    }

    @GetMapping("/user/propchannel")
    public List<Channel> getChannel(@RequestParam(value = "idUser") int idUser) {
        return userService.getUserOwnerChannel(idUser);
    }

    @GetMapping("/user/channel")

    @PostMapping(value = "/create")
    public void create(){
        User user = new User();
        user.setFirstname("Cédric");
        user.setLastname("Martinet");
        user.setEmail("cedric.martinet@utc.fr");
        userService.saveUser(user);
    }

    @PostMapping(value="/update")


    @GetMapping(value = "/testmail")
    public void testmail(){
        //Test envoi Mail
        JakartaEmail jakartaEmail = new JakartaEmail();
        jakartaEmail.sendMail();
    }

    // connection User



}
