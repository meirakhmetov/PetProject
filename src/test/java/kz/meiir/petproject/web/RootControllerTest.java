package kz.meiir.petproject.web;

import org.junit.jupiter.api.Test;

import static kz.meiir.petproject.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static kz.meiir.petproject.UserTestData.ADMIN;

/**
 * @author Meiir Akhmetov on 14.02.2023
 */
class RootControllerTest extends AbstractControllerTest {

    RootControllerTest(){
        super("");
    }

    @Test
    void getUsers() throws Exception{
        perform(doGet("users").auth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void unAuth() throws Exception {
        perform(doGet("users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getMeals() throws Exception{
        perform(doGet("meals").auth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"));
    }
}
