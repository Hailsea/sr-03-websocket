package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.UserDTO;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping(value="/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(
                    modelMapper.map(user, UserDTO.class),
                    HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    /* MAIL */
    /*@GetMapping(value = "/testmail")
    public void testmail(){

        //Test envoi Mail
        JakartaEmail jakartaEmail = new JakartaEmail();
        jakartaEmail.sendMail();
    }*/
}