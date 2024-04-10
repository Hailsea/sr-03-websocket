package fr.utc.sr03.services;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ChannelService {
    @PersistenceContext
    EntityManager em;

    //CRUD
    public void addChannel(Channel c) {
        em.persist(c);
    }

    public void updateChannel(Channel c) {
        em.merge(c);
    }

    public Channel getChannel(int id) {
        return em.find(Channel.class, id);
    }

    public void deleteChannel(int id) {
        em.remove(getChannel(id));
    }

    public void addInvitation(Invitation invitation) { em.persist(invitation);}

    public void updateInvitation(Invitation invitation) { em.merge(invitation);}

    public Invitation getInvitation(int id) {
        return em.find(Invitation.class, id);
    }

    public void deleteInvitation(int id) {
        em.remove(getInvitation(id));
    }


    //Other operations

    public List<Channel> getChannels() {
        Query q = em.createQuery("select c FROM Channel c");
        return q.getResultList();
    }

    public List<Invitation> getInvitations(){
        Query q = em.createQuery("select i FROM Invitation i");
        return q.getResultList();
    }

    public List<Invitation> getInvitationsForUserInvited(User userInvited) {
        Query q = em.createQuery("SELECT i FROM Invitation i WHERE i.invited.id = :userId");
        q.setParameter("userId", userInvited.getId());
        return q.getResultList();
    }

}
