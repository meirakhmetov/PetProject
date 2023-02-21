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

    private static final String REST_URL = MealRestController.REST_URL+'/';

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), MEAL1));
    }

    @Test
    void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void update() throws Exception{
        Meal updated = getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(mealService.get(MEAL1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Meal created = readFromJson(action, Meal.class);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID),newMeal);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getTos(MEALS, USER.getCaloriesPerDay())));
    }


    @Test
    void getBetween() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "between?startDateTime=2023-01-01T07:00&endDateTime=2023-01-02T11:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createTo(MEAL5,true), createTo(MEAL1, false)));
    }
}