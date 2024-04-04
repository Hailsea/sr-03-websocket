package fr.utc.sr03.services;


import fr.utc.sr03.model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public class ServicesRequest {

    @PersistenceContext
    EntityManager em;



    public void addUser(Users user){
        em.persist(user);
    }

    @SuppressWarnings("unchecked")
    public List<Users> getUsers(){
        Query q = em.createQuery("select u from Users u");
        return q.getResultList();
    }





}
