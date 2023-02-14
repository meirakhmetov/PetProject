package kz.meiir.petproject.service.datajpa;

import kz.meiir.petproject.UserTestData;
import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.service.AbstractMealServiceTest;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.MealTestData.*;
import static kz.meiir.petproject.Profiles.DATAJPA;
import static kz.meiir.petproject.UserTestData.ADMIN_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(DATAJPA)
class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    void getWithUser() throws Exception{
        Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(adminMeal,ADMIN_MEAL1);
        UserTestData.assertMatch(adminMeal.getUser(),UserTestData.ADMIN);
    }

    @Test
    void getWithUserNotFound() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.getWithUser(1,ADMIN_ID));
    }
}
