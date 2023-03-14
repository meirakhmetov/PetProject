package kz.meiir.petproject.web.json;

import org.junit.jupiter.api.Test;
import kz.meiir.petproject.UserTestData;
import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void writeOnlyAccess() throws Exception{
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = JsonUtil.writeAdditionProps(UserTestData.USER, "password","newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}
