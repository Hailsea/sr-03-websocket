package fr.utc.sr03.services;

import fr.utc.sr03.model.Channel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ServicesChannelRequest {
    @PersistenceContext
    EntityManager em;

    // Handle channel
    public void addChannel(Channel c) { em.persist(c); }

    public void updateChannel(Channel c) { em.merge(c); }
    public List<Channel> getChannels() {
        Query q = em.createQuery("select c FROM Channel c");
        return q.getResultList();
    }

    public Channel getChannelByID(int id) {
        return em.find(Channel.class, id);
    }

}
