package fr.utc.sr03.controller;


import fr.utc.sr03.model.Users;
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


        Users user = new Users();
        user.setFirstname("Cédric");
        user.setLastname("Martinet");
        user.setMail("cedric.martinet@utc.fr");
        servicesRequest.addUser(user);
    }

    @GetMapping(value = "/liste")
    public List<Users> getUsers(){
        return servicesRequest.getUsers();
    }


}
