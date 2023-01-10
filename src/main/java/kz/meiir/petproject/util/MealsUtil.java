package kz.meiir.petproject.util;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.to.MealTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,10,0),"Завтрак",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,13,0),"Обед",1000),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,20,0),"Ужин",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,10,0),"Завтрак",1000),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,13,0),"Обед",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,20,0),"Ужин",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,23,0),"Поздний ужин",500)
        );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay){
        return getFiltered(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, @Nullable LocalTime startTime, @Nullable LocalTime endTime){
        return getFiltered(meals,caloriesPerDay, meal-> Util.isBetweenInclusive(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> getFiltered(Collection<Meal> meals,  int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
//                        Collectors.groupingBy(Meal::getDate,Collectors.summingInt(Meal::getCalories))
                        Collectors.toMap(Meal::getDate,Meal::getCalories,Integer::sum)
                );
        return meals.stream()
                .filter(filter)
                .map(meal ->creatTo(meal, caloriesSumByDate.get(meal.getDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo creatTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess);
    }
}
