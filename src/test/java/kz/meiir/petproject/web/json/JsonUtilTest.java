package kz.meiir.petproject.web.json;


import com.fasterxml.jackson.databind.ObjectWriter;
import kz.meiir.petproject.View;
import kz.meiir.petproject.model.Meal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kz.meiir.petproject.MealTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Meiir Akhmetov on 17.02.2023
 */
public class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception{
        String json = JsonUtil.writeValue(ADMIN_MEAL1);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json,Meal.class);
        MEAL_MATCHERS.assertMatch(meal,ADMIN_MEAL1);
    }

    @Test
    void readWriteValues() throws Exception{
        String json = JsonUtil.writeValue(MEALS);
        System.out.println(json);
        List<Meal> meals = JsonUtil.readValues(json,Meal.class);
        MEAL_MATCHERS.assertMatch(meals,MEALS);
    }

    @Test
    public void testWriteWithView() throws Exception {
        ObjectWriter uiWriter = JacksonObjectMapper.getMapper().writerWithView(View.JsonUI.class);
        String json = JsonUtil.writeValue(ADMIN_MEAL1, uiWriter);
        System.out.println(json);
        assertThat(json, containsString("dateTimeUI"));
    }
}
