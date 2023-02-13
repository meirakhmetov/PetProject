package kz.meiir.petproject.service.datajpa;

import kz.meiir.petproject.MealTestData;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.AbstractJpaUserServiceTest;
import kz.meiir.petproject.service.AbstractUserServiceTest;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.DATAJPA;
import static kz.meiir.petproject.UserTestData.*;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {
    @Test
    public void getWithMeals() throws Exception{
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(),MealTestData.MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void getWithMealsNotFound() throws Exception{
        service.getWithMeals(1);
    }
}
