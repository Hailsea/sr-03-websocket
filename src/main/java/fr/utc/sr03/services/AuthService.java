package fr.utc.sr03.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.exception.UnauthorizedException;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.JakartaEmail;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    private JakartaEmail jakartaEmail=new JakartaEmail("testSR03@gmx.fr","y.L4i#m&i3Qw>-%");

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
            throw new UnauthorizedException("incorrect email or password");
        }
        return user;
    }

    private String generateRandomPassword(){
        int length=12;
        String chars = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-+<>ABCDEFGHIJKLMNOP1234567890";
        String pw = "";
        for (var x = 0; x < length; x++) {
            int i = (int)Math.round(Math.floor(Math.random() * chars.length()));
            pw += chars.charAt(i);
        }
        return pw;
    }

    public void resetPassword(String email){
        User user_to_reset=userService.getUserByEmail(email);
        if (user_to_reset!=null){
            String a = generateRandomPassword();
            user_to_reset.setPassword(a);
            userService.updateUser(user_to_reset);
            jakartaEmail.sendMail("testSR03@gmx.fr","testSR03@gmx.fr","mail.gmx.net","587","[DO NOT REPLY] Password reset",String.format("Vous avez demandez une réinitialisation de votre mot de passe. Si vous n'en étiez pas à l'origine. Tant pis.\n Votre nouveau mot de passe : %s", a));
        }
    }


}