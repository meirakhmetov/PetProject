package kz.meiir.petproject.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.service.MealService;
import kz.meiir.petproject.util.exception.NotFoundException;
import kz.meiir.petproject.web.AbstractControllerTest;
import kz.meiir.petproject.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static kz.meiir.petproject.MealTestData.*;
import static kz.meiir.petproject.TestUtil.readFromJson;
import static kz.meiir.petproject.TestUtil.readFromJsonMvcResult;
import static kz.meiir.petproject.UserTestData.USER;
import static kz.meiir.petproject.UserTestData.USER_ID;
import static kz.meiir.petproject.model.AbstractBaseEntity.START_SEQ;
import static kz.meiir.petproject.util.MealsUtil.createTo;
import static kz.meiir.petproject.util.MealsUtil.getTos;


/**
 * @author Meiir Akhmetov on 20.02.2023
 */
class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    MealRestControllerTest(){
        super(MealRestController.REST_URL);
    }

    @Test
    void get() throws Exception{
        perform(doGet(MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> MEAL_MATCHERS.assertMatch(readFromJsonMvcResult(result, Meal.class), MEAL1));
    }

    @Test
    void delete() throws Exception{
        perform(doDelete(MEAL1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void update() throws Exception{
        Meal updated = getUpdated();

        perform(doPut(MEAL1_ID).jsonBody(updated))
                .andExpect(status().isNoContent());

        MEAL_MATCHERS.assertMatch(mealService.get(MEAL1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = perform(doPost().jsonBody(newMeal));

        Meal created = readFromJson(action, Meal.class);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MEAL_MATCHERS.assertMatch(created, newMeal);
        MEAL_MATCHERS.assertMatch(mealService.get(newId, USER_ID),newMeal);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHERS.contentJson(getTos(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    void filter() throws Exception{
        perform(doGet("filter").unwrap()
                .param("startDate", "2023-01-01").param("startTime","07:00")
                .param("endDate","2023-01-02").param("endTime","11:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MEAL_TO_MATCHERS.contentJson(createTo(MEAL5,true), createTo(MEAL1, false)));
    }

    @Test
    void filterAll() throws Exception{
        perform(doGet("filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHERS.contentJson(getTos(MEALS,USER.getCaloriesPerDay())));
    }
}