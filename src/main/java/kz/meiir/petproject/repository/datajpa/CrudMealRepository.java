package kz.meiir.petproject.repository.datajpa;

import kz.meiir.petproject.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Meiir Akhmetov on 06.02.2023
 */
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
}
