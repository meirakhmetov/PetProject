package kz.meiir.petproject.repository;

import javafx.print.Collation;
import kz.meiir.petproject.model.Meal;

import java.util.Collection;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public interface MealRepository {
    //null if not found, when update
    Meal save(Meal meal);

    //false if not found
    boolean delete(int id);

    //null if not found
    Meal get(int id);

    Collection<Meal> getAll();
}
