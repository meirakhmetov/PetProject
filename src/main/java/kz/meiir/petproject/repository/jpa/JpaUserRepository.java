package kz.meiir.petproject.repository.jpa;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Meiir Akhmetov on 23.01.2023
 */
@Repository
public class JpaUserRepository implements UserRepository {

/*
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
 */
    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        if(user.isNew()){
            em.persist(user);
            return user;
        }else{
            return em.merge(user);
        }
    }

    @Override
    public User get(int id) {
        return em.find(User.class,id);
    }

    @Override
    public boolean delete(int id) {
        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id",id).executeUpdate()!=0;
    }



    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
