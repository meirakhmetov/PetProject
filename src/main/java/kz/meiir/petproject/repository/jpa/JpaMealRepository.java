package kz.meiir.petproject.repository.jpa;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.MealRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Meiir Akhmetov on 23.01.2023
 */
@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if(meal.isNew()){
            em.persist(meal);
            return meal;
        }else if(get(meal.getId(), userId) == null){
            return null;
        }
        return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DElETE)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .executeUpdate()!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        var meal = em.find(Meal.class, id);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId",userId)
                .setParameter("startDate", startDateTime)
                .setParameter("endDate",endDateTime)
                .getResultList();
    }
}
