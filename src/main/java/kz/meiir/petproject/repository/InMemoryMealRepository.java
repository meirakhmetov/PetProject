package kz.meiir.petproject.repository;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(),meal);
            return meal;
        }
        //treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(),(id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id)!=null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
