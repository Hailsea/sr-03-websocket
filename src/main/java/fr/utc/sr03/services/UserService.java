package fr.utc.sr03.services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    // CREATE
    public void saveUser(User user) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        // BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        user.setPassword(bcryptHashString);
        userRepository.save(user);
    }

    // UPDATE
    public void updateUser(User user) {
        User oldUser = getUserById(user.getId());
        user.setPassword(oldUser.getPassword());
        userRepository.save(user);
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

}
