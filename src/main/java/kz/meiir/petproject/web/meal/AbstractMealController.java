package kz.meiir.petproject.web.meal;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.service.MealService;
import kz.meiir.petproject.to.MealTo;
import kz.meiir.petproject.util.MealsUtil;
import kz.meiir.petproject.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.assureIdConsistent;
import static kz.meiir.petproject.util.ValidationUtil.checkNew;

/**
 * @author Meiir Akhmetov on 13.02.2023
 */
public abstract class AbstractMealController {
    private static final Logger Log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal get(int id){
        int userId = SecurityUtil.authUserId();
        Log.info("get meal {} for user {}",id,userId);
        return service.get(id,userId);
    }

    public void delete(int id){
        int userId = SecurityUtil.authUserId();
        Log.info("delete meal {} for user {}", id, userId);
        service.delete(id,userId);
    }

    public List<MealTo> getAll(){
        int userId = SecurityUtil.authUserId();
        Log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId),SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal){
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        Log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id){
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal,id);
        Log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime){
        int userId = SecurityUtil.authUserId();
        Log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate,endDate,userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime,endTime);
    }

}
