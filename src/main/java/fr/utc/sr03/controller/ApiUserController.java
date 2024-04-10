package fr.utc.sr03.controller;


import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiUserController {

    @Resource
    private UserService userService;


    @PostMapping(value = "/api/users/create")
    public void create(@RequestBody User entity){
        userService.addUser(entity);
    }

    @GetMapping(value = "/api/users/list")
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
