package fr.utc.sr03.repository;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}
