package kz.meiir.petproject.repository.jpa;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.jpa.QueryHints;

import java.util.List;

/**
 * @author Meiir Akhmetov on 23.01.2023
 */
@Repository
@Transactional(readOnly = true)
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
    @Transactional
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
    @Transactional
    public boolean delete(int id) {
//        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate()!=0;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter(1,email)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
