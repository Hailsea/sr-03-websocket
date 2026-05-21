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
    public int saveUser(User user) {
        String email=user.getEmail();
        if (!checkEmailFormat(email)){
            return -1;
        }
        if (!checkPasswordFormat(user.getPassword())){
            return -2;
        }
        if (getUserByEmailAddress(email)!=null){
            return -3;
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

            userRepository.save(user);
        }
        return 1;
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

    public String generateRandomPassword(){
        int length=12;
        String chars = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-+<>ABCDEFGHIJKLMNOP1234567890";
        String pw = "";
        for (var x = 0; x < length; x++) {
            int i = (int)Math.round(Math.floor(Math.random() * chars.length()));
            pw += chars.charAt(i);
        }
        return pw;
    }

    public boolean checkPasswordFormat(String password){return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)(.{8,})");}

    public boolean checkEmailFormat(String password){return password.matches("[a-zA-Z]\\w+(\\.\\w+)*@[a-zA-Z]+\\.[a-zA-Z]{1,3}");}

    public List<Channel> getChannelOwner (int idUser) {
        return userRepository.getChannelOwner(idUser);
    }

    public List<Channel> getChannelUser(int idUser) {
        return userRepository.getChannelUser(idUser);
    }
}
