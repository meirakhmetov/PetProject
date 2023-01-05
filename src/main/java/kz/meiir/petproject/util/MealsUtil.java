package kz.meiir.petproject.util;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class MealsUtil {
    private static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args){
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,10,0),"Завтрак",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,13,0),"Обед",1000),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,1,20,0),"Ужин",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,10,0),"Завтрак",1000),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,13,0),"Обед",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,20,0),"Ужин",500),
                new Meal(LocalDateTime.of(2023, Month.JANUARY,2,23,0),"Поздний ужин",500)
        );
        LocalTime startTime = LocalTime.of(7,0);
        LocalTime endTime = LocalTime.of(12,0);

        List<MealTo> mealTo = getFiltered(meals, startTime,endTime,DEFAULT_CALORIES_PER_DAY);
        mealTo.forEach(System.out::println);

        System.out.println(getFilteredByCycle(meals, startTime,endTime,DEFAULT_CALORIES_PER_DAY));
    }


    public static List<MealTo> getFiltered(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
//                        Collectors.groupingBy(Meal::getDate,Collectors.summingInt(Meal::getCalories))
                        Collectors.toMap(Meal::getDate,Meal::getCalories,Integer::sum)
                );
        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(),startTime,endTime))
                .map(meal ->
                        new MealTo(meal.getDateTime(),meal.getDescription(),meal.getCalories(),caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate,Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsTo = new ArrayList<>();
        meals.forEach(meal -> {
            if(TimeUtil.isBetween(meal.getTime(),startTime,endTime)){
                mealsTo.add(creatTo(meal,caloriesSumByDate.get(meal.getDate())>caloriesPerDay));
            }
        });
        return mealsTo;
    }


    private static MealTo creatTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess);
    }
}
