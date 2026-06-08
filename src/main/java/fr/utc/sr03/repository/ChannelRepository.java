package fr.utc.sr03.repository;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Integer id);

    Page<Channel> findByOwner(User owner, Pageable pageable);

    SELECT c FROM User u JOIN u.channels c WHERE u = :user")
    Page<Channel> findByUserTarget(User user, Pageable pageable);

    @Query("SELECT i.channel FROM Invitation i WHERE i.userTarget = ?1 AND i.stateInitation = true")
    Page<Channel> findByInvitation(User user, Pageable pageable);
}
