package kz.meiir.petproject.repository;

import kz.meiir.petproject.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */

public interface MealRepository {
    //null if not found, when update
    Meal save(Meal meal, int userId);

    //false if not found
    boolean delete(int id, int userId);

    //null if not found
    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

}
