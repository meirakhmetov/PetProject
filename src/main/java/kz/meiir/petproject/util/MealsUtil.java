package kz.meiir.petproject.util;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.to.MealTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class MealsUtil {

    private MealsUtil() {
    }

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
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess);
    }
}
