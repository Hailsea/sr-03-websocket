package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.UserDTO;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ModelMapper modelMapper;

    /* Avoir la liste des utilisateurs pour envoyer une invitation */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(value = "exclude") Integer exclude) {

        List<UserDTO> users = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            if (exclude != null && user.getId() == exclude) {
                continue;
            }
            users.add(modelMapper.map(user, UserDTO.class));
        }

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    /*@GetMapping(value = "/testmail")
    public void testmail(){

        //Test envoi Mail
        JakartaEmail jakartaEmail = new JakartaEmail();
        jakartaEmail.sendMail();
    }*/
}