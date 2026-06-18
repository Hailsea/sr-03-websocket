package fr.utc.sr03.services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (oldUser != null) {
            oldUser.setLastname(user.getLastname());
            oldUser.setFirstname(user.getFirstname());

            if (user.getEmail() != null) {
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

    // Retourne les users selon le filtre
    public List<User> getUsersByStatus(String status) {
        return switch (status) {
            case "active" -> userRepository.findByAlive(true);
            case "inactive" -> userRepository.findByAlive(false);
            default -> userRepository.findAll();
        };
    }

    // DELETE
    public boolean deleteUser(int id) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }
        // Si l'utilisateur est admin et qu'il est le seul restant
        if (user.getAdmin() && userRepository.countAdmins() <= 1) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public boolean toggleActive(int id, int currentUserId) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }
        // Interdit de desactiver son propre compte
        if (id == currentUserId && user.getAlive()) {
            return false;
        }
        user.setAlive(!user.getAlive());
        userRepository.save(user);
        return true;
    }

    public boolean checkPasswordFormat(String password){return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)(.{8,})");}

    public boolean checkEmailFormat(String password){return password.matches("[a-zA-Z]\\w+(\\.\\w+)*@[a-zA-Z]+\\.[a-zA-Z]{1,3}");}

    public void joinChannel(User user, Channel channel) {
        if (user.getChannels() == null) {
            user.setChannels(new ArrayList<>());
        }

        if (!user.getChannels().contains(channel)) {
            user.getChannels().add(channel);
        }
        userRepository.save(user);
    }

    public void leaveChannel(User user, int channelId) {
        if (user.getChannels() != null) {
            user.getChannels().removeIf(c -> c.getId() != null && c.getId() == channelId);
            userRepository.save(user);
        }
    }
}
