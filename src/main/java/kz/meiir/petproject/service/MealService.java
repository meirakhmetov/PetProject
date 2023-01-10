package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.repository.MealRepository;
import kz.meiir.petproject.util.DateTimeUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
        return repository.getBetween(
                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX),userId);
    }

    public List<Meal> getAll (int userId){
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId){
        checkNotFoundWithId(repository.save(meal, userId),meal.getId());
    }

    public Meal create(Meal meal, int userId){
        return repository.save(meal, userId);
    }
}
