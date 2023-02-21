package kz.meiir.petproject.service.datajpa;

import kz.meiir.petproject.MealTestData;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.AbstractUserServiceTest;
import kz.meiir.petproject.util.exception.NotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.DATAJPA;
import static kz.meiir.petproject.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() throws Exception{
        User admin = service.getWithMeals(ADMIN_ID);
        assertMatch(admin, ADMIN);
        MealTestData.assertMatch(admin.getMeals(),MealTestData.ADMIN_MEAL2, MealTestData.ADMIN_MEAL1);
    }

    @Test
    public void getWithMealsNotFound() throws Exception{
        assertThrows(NotFoundException.class, () ->
                service.getWithMeals(1));
    }
}
