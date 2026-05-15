package fr.utc.sr03.services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    // CREATE or UPDATE
    public int saveUser(User user) {
        if (!checkEmailFormat(user.getEmail())){
            return -1;
        }
        if (!checkPasswordFormat(user.getPassword())){
            return -2;
        }

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(bcryptHashString);
        userRepository.save(user);
        return user.getId();
    }

    // UPDATE
    public void updateUser(User user) {
        User oldUser = getUserById(user.getId());
        if (oldUser != null) {
            oldUser.setLastname(user.getLastname());
            oldUser.setFirstname(user.getFirstname());

            if (user.getEmail() != null && checkEmailFormat(user.getEmail())) {
                oldUser.setEmail(user.getEmail());
            }

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                if (checkPasswordFormat(user.getPassword())) {
                    String encryptPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
                    oldUser.setPassword(encryptPassword);
                }
            }

            userRepository.save(user);
        }
    }

    // READ
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByEmailAddress(String emailAddress) {return userRepository.findByEmailAddress(emailAddress);}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // DELETE
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public boolean checkPasswordFormat(String password){return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)(.{8,})");}

    public boolean checkEmailFormat(String password){return password.matches("[a-zA-Z]\\w+(\\.\\w+)*@[a-zA-Z]+\\.[a-zA-Z]{1,3}");}

    public List<Channel> getUserOwnerChannel (int idUser) {
        return userRepository.getOwnerChannel(idUser);
    }
}
