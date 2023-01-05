package kz.meiir.petproject.util;

import kz.meiir.petproject.model.UserMeal;
import kz.meiir.petproject.model.UserMealMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class UserMealsUtil {
    public static void main(String[] args){
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 1,10,0),"Завтрак",500),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 1,13,0),"Обед",1000),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 1,20,0),"Ужин",500),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 2,0,0),"Еда на граничное значение",100),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 2,10,0),"Завтрак",1000),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 2,13,0),"Обед",500),
                new UserMeal(LocalDateTime.of(2023, Month.JANUARY, 2,20,0),"Ужин",410)
        );

        List<UserMealMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7,0), LocalTime.of(12,0),2000);
        mealsTo.forEach(System.out::println);

      System.out.println(filteredByStreams(meals,LocalTime.of(7,0),LocalTime.of(12,0),2000));
    }


    public static List<UserMealMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return null;
    }
    private static List<UserMealMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return null;
    }



}
