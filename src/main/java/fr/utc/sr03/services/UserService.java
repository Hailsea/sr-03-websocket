package fr.utc.sr03.services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    // CREATE or UPDATE
    public int saveUser(User user, String passwordConfirmation) {
        String email=user.getEmail();
        if (!checkEmailFormat(email)){
            return -1;
        }
        if (!checkPasswordFormat(user.getPassword())){
            return -2;
        }
        if (userRepository.findByEmail(email)!=null){
            return -3;
        }
        if (!user.getPassword().equals(passwordConfirmation)){
            return -4;
        }
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(bcryptHashString);
        userRepository.save(user);
        return 1;
    }

    // UPDATE
    public int updateUser(User user) {
        User oldUser = getUserById(user.getId());
        System.out.println("User:"+user);
        System.out.println("Userid:"+user.getId());
        System.out.println("Userpw:"+user.getPassword());
        System.out.println(oldUser);
        if (oldUser != null) {
            oldUser.setLastname(user.getLastname());
            oldUser.setFirstname(user.getFirstname());

            if (user.getEmail() != null) {
                System.out.println(user.getEmail());
                if (!checkEmailFormat(user.getEmail())){
                    return -1;
                }
                oldUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                if (checkPasswordFormat(user.getPassword())) {
                    String encryptPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
                    oldUser.setPassword(encryptPassword);
                }
                else{
                    return -2;
                }
            }

            userRepository.save(oldUser);
        }
        return 1;
    }

    // READ
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByEmail(String emailAddress) {return userRepository.findByEmail(emailAddress);}
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // DELETE
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public boolean checkPasswordFormat(String password){return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)(.{8,})");}

    public boolean checkEmailFormat(String password){return password.matches("[a-zA-Z]\\w+(\\.\\w+)*@[a-zA-Z]+\\.[a-zA-Z]{1,3}");}
}
