package kz.meiir.petproject.web;


import kz.meiir.petproject.model.User;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kz.meiir.petproject.MealTestData.MEALS;
import static kz.meiir.petproject.UserTestData.*;


import static kz.meiir.petproject.util.MealsUtil.getTos;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Meiir Akhmetov on 14.02.2023
 */
class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception{
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError{
                                  assertMatch(actual,ADMIN,USER);
                            }
                        }
                ));
    }

    @Test
    void testMeals() throws Exception{
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", getTos(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }
}
