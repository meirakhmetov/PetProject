package kz.meiir.petproject.repository.inmemory;

import kz.meiir.petproject.MealTestData;
import kz.meiir.petproject.UserTestData;
import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.repository.MealRepository;
import kz.meiir.petproject.util.MealsUtil;
import kz.meiir.petproject.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static kz.meiir.petproject.UserTestData.ADMIN_ID;
import static kz.meiir.petproject.UserTestData.USER_ID;


/**
 * @author Meiir Akhmetov on 09.01.2023
 */
@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger Log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer,InMemoryBaseRepository<Meal>> usersMealsMap = new ConcurrentHashMap<>();

    {
        InMemoryBaseRepository<Meal> userMeals = new InMemoryBaseRepository<>();
        usersMealsMap.put(USER_ID, userMeals);
        MealTestData.MEALS.forEach(meal ->userMeals.map.put(meal.getId(),meal));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal, "meal must not be null");
        InMemoryBaseRepository<Meal> meals = usersMealsMap.computeIfAbsent(userId,uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @PostConstruct
    public void postConstruct(){
        Log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy(){
        Log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals!=null && meals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals==null ? null :  meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal ->true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId){
        Objects.requireNonNull(startDateTime, "startDateTime must not be null");
        Objects.requireNonNull(endDateTime, "endDateTime must not be null");
        return getAllFiltered(userId, meal -> Util.isBetweenInclusive(meal.getDateTime(),startDateTime,endDateTime));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals==null ? Collections.emptyList():
                meals.getCollection().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
