package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static kz.meiir.petproject.MealTestData.*;
import static kz.meiir.petproject.UserTestData.ADMIN_ID;
import static kz.meiir.petproject.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Meiir Akhmetov on 20.01.2023
 */
public abstract class AbstractMealServiceTest extends AbstractServiceTest{

    @Autowired
    protected MealService service;

    @Test
    void delete() throws Exception{
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(MEAL1_ID,USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.delete(1,USER_ID));
    }
    @Test
    void deleteNotOwn() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.delete(MEAL1_ID,ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal,USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MEAL_MATCHERS.assertMatch(created,newMeal);
        MEAL_MATCHERS.assertMatch(service.get(newId,USER_ID),newMeal);
    }

    @Test
    void get() throws Exception{
        Meal actual = service.get(ADMIN_MEAL_ID,ADMIN_ID);
        MEAL_MATCHERS.assertMatch(actual,ADMIN_MEAL1);
    }

    @Test
    void getNotFound() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.get(1,USER_ID));
    }

    @Test
    void getNotOwn() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.get(MEAL1_ID,ADMIN_ID));
    }

    @Test
    void update() throws Exception{
        Meal updated = getUpdated();
        service.update(updated,USER_ID);
        MEAL_MATCHERS.assertMatch(service.get(MEAL1_ID,USER_ID),updated);
    }

    @Test
    void updateNotFound() throws Exception{
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(MEAL1,ADMIN_ID));
        assertEquals(e.getMessage(), "Not found entity with id=" + MEAL1_ID);
    }

    @Test
    void getAll() throws Exception {
        MEAL_MATCHERS.assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    void getBetween() throws Exception {
        MEAL_MATCHERS.assertMatch(service.getBetweenDates(
                LocalDate.of(2023, Month.JANUARY,1),
                LocalDate.of(2023, Month.JANUARY,1),USER_ID),MEAL3,MEAL2,MEAL1);
    }

    @Test
    void getBetweenWithNullDates() throws Exception{
        MEAL_MATCHERS.assertMatch(service.getBetweenDates(null,null,USER_ID),MEALS);
    }

    @Test
    void createWithException() throws Exception{
        Assumptions.assumeTrue(isJpaBased(),"Validation not support (JPA only)");
        validateRootCause(()-> service.create(new Meal(null,of(2023,Month.JANUARY,1,18,0)," ",300),USER_ID), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new Meal(null,null,"Description",300),USER_ID), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new Meal(null,of(2023,Month.JANUARY,1,18,0),"Description",9),USER_ID), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new Meal(null,of(2023,Month.JANUARY,1,18,0),"Description",5001),USER_ID), ConstraintViolationException.class);
    }

}