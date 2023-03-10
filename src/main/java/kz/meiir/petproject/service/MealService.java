package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.repository.MealRepository;;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */

@Service
public class MealService{

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId){
        return checkNotFoundWithId(repository.get(id,userId),id);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repository.delete(id,userId),id);
    }

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
        return repository.getBetweenInclusive(startDate, endDate, userId);
    }

    public List<Meal> getAll (int userId){
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId){
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(repository.save(meal, userId),meal.getId());
    }

    public Meal create(Meal meal, int userId){
        Assert.notNull(meal,"meal must not be null");
        return repository.save(meal, userId);
    }

    public Meal getWithUser(int id, int userId){
        return checkNotFoundWithId(repository.getWithUser(id,userId),id);
    }
}
