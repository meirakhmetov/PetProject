package kz.meiir.petproject.web.json;

import kz.meiir.petproject.model.Meal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kz.meiir.petproject.MealTestData.*;

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
}
