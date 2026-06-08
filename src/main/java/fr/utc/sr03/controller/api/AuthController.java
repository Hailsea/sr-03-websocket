package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.LoginDTO;
import fr.utc.sr03.dto.UserDTO;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.AuthService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private ModelMapper modelMapper;

    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO login) {
        User user = authService.authenticate(login.getEmail(), login.getPassword());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }
}
