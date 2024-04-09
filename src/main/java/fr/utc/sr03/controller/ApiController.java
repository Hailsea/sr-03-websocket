package fr.utc.sr03.controller;


import fr.utc.sr03.model.User;
import fr.utc.sr03.services.ServicesRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Resource
    private ServicesRequest servicesRequest;


    @PostMapping(value = "/create")
    public void create(){
        User user = new User("Cédric", "Martinet", "cedric.martinet@utc.fr", "mypassword", false);
        servicesRequest.addUser(user);
    }

    @GetMapping(value = "/list")
    public List<User> getUsers(){
        return servicesRequest.getUsers();
    }

}
