package fr.utc.sr03.repository;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u from User u where u.email = ?1")
    User findByEmailAddress(String emailAddress);

    @Query("select u.ownerChannel from User u where u.id = ?1")
    List<Channel> getOwnerChannel(int id);

}
