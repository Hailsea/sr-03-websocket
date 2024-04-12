package fr.utc.sr03.services;


import fr.utc.sr03.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserService {
    @PersistenceContext
    EntityManager em;

    //CRUD

    public void addUser(User user){

        em.persist(user);
    }

    public User getUser(int id){
        //return un user via la clé primaire
        return em.find(User.class, id);
    }

    public void updateUser(User user){
        em.merge(user);
    }

    public void deleteUser(int id){
        //return un user via la clé primaire
        em.remove(getUser(id));
    }

    //Other operations

    @SuppressWarnings("unchecked")
    public List<User> getUsers(){
        Query q = em.createQuery("select u FROM User u");
        return q.getResultList();
    }
}
