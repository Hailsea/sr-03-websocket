package fr.utc.sr03.repository;

import fr.utc.sr03.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

    // Compte les administrateurs
    @Query("select count(u) from User u where u.isAdmin = true")
    long countAdmins();

    // Liste les utilisateurs isalive
    @Query("select u from User u where u.isAlive = ?1")
    List<User> findByAlive(boolean alive);
}
