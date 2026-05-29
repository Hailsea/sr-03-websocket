package fr.utc.sr03.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.dto.Login;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import fr.utc.sr03.services.ChannelService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api")
public class ApiController {

    @Resource
    private UserService userService;

    @Resource
    private ChannelService channelService;

    /*@GetMapping(value = "/liste")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }*/

    /*@GetMapping(value = "/oneUser/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginUser) {
        User user = userService.getUserByEmailAddress(loginUser.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "email introuvable"));
        }
        if (BCrypt.verifyer().verify(loginUser.getPassword().toCharArray(), user.getPassword()).verified) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("firstname", user.getFirstname());
            response.put("lastname", user.getLastname());
            response.put("email", user.getEmail());
            response.put("isAdmin", user.getAdmin());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "mot de passe incorrect"));
        }
    }

    @GetMapping("/user")
    public User getUser(@RequestParam(value = "idUser") int idUser) {
        User u = userService.getUserById(idUser);
        u.setPassword("*");
        return u;
    }

    @GetMapping("/user/channelowner")
    public List<Channel> getChannelOwner(@RequestParam(value = "idUser") int idUser) {
        return userService.getChannelOwner(idUser);
    }

    @GetMapping("/user/channel")
    public List<Channel> getUserOwnerChannel(@RequestParam(value = "idUser") int idUser) {
        return userService.getChannelOwner(idUser);
    }

    @PostMapping("/channel")
    public Channel createChannel(@RequestBody Channel channel) {
        return channelService.createChannel(channel);
    }

    @PutMapping("/channel")
    public Channel updateChannel(@RequestBody Channel channel) {
        return channelService.updateChannel(channel);
    }

    @DeleteMapping("/channel")
    public Channel deleteChannel(@RequestParam(value = "id") int idChannel) {
        return channelService.deleteChannel(idChannel);
    }

    /*@PostMapping(value = "/create")
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
    }*/

}
