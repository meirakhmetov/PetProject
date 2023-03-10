package kz.meiir.petproject;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.to.MealTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static kz.meiir.petproject.TestUtil.readListFromJsonMvcResult;
import static kz.meiir.petproject.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Meiir Akhmetov on 20.01.2023
 */
public class MealTestData {
    public static final int MEAL1_ID = START_SEQ+2;
    public static final int ADMIN_MEAL_ID = START_SEQ+9;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, of(2023, Month.JANUARY,01,10,0),"Завтрак",500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID+1, of(2023, Month.JANUARY,01,13,0),"Обед",1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID+2, of(2023, Month.JANUARY,01,20,0),"Ужин",500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID+3, of(2023, Month.JANUARY,02,0,0),"Еда на граничное занчение",100);
    public static final Meal MEAL5 = new Meal(MEAL1_ID+4, of(2023, Month.JANUARY,02,10,0),"Завтрак",500);
    public static final Meal MEAL6 = new Meal(MEAL1_ID+5, of(2023, Month.JANUARY,02,13,0),"Обед",1000);
    public static final Meal MEAL7 = new Meal(MEAL1_ID+6, of(2023, Month.JANUARY,02,20,0),"Ужин",510);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2023, Month.JANUARY,02,14,0),"Админ ланч",510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID+1, of(2023, Month.JANUARY,02,21,0),"Админ ужин",1500);

    public static final List<Meal> MEALS = List.of(MEAL7,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);

    public static Meal getNew(){
        return new Meal(null, of(2023,Month.JANUARY,2,18,0),"Созданные ужин",300);
    }

    public static Meal getUpdated(){
        return new Meal(MEAL1_ID, MEAL1.getDateTime(),"Обнавленный завтрак",200);
    }

    public static TestMatchers<Meal> MEAL_MATCHERS = TestMatchers.useFieldsComparator(Meal.class,"user");
    public static TestMatchers<MealTo> MEAL_TO_MATCHERS = TestMatchers.useEquals(MealTo.class);

}
