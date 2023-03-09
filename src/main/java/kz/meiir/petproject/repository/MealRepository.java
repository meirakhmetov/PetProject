package kz.meiir.petproject.repository;

import kz.meiir.petproject.model.Meal;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static kz.meiir.petproject.util.DateTimeUtil.getEndExclusive;
import static kz.meiir.petproject.util.DateTimeUtil.getStartInclusive;

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

    default List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
        return getBetweenInclusive(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    };

    List<Meal> getBetweenInclusive(@NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate, int userId);

    default Meal getWithUser(int id, int userId){
        throw new UnsupportedOperationException();
    }

}
