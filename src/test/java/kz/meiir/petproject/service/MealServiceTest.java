package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Meal;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import static kz.meiir.petproject.MealTestData.*;
import static kz.meiir.petproject.UserTestData.ADMIN_ID;
import static kz.meiir.petproject.UserTestData.USER_ID;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Meiir Akhmetov on 20.01.2023
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger Log = getLogger("result");

    private static StringBuilder results = new StringBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    //http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description){
            String result = String.format("\n%-25s %8d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            Log.info(result+" ms\n");
        }
    };

    @AfterClass
    public static void printResult(){
        Log.info("\n--------------------------" +
                "\nTest          Duration, ms" +
                "\n--------------------------" +
                results +
                "\n--------------------------");
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception{
        service.delete(MEAL1_ID, USER_ID);
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID,USER_ID);
    }

    @Test
    public void deleteNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        service.delete(1,USER_ID);
    }
    @Test
    public void deleteNotOwn() throws Exception{
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID,ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal,USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created,newMeal);
        assertMatch(service.get(newId,USER_ID),newMeal);
    }

    @Test
    public void get() throws Exception{
        Meal actual = service.get(ADMIN_MEAL_ID,ADMIN_ID);
        assertMatch(actual,ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        service.get(1,USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception{
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID,ADMIN_ID);
    }

    @Test
    public void update() throws Exception{
        Meal updated = getUpdated();
        service.update(updated,USER_ID);
        assertMatch(service.get(MEAL1_ID,USER_ID),updated);
    }

    @Test
    public void updateNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.update(MEAL1,ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetweenDates() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2023, Month.JANUARY,1),
                LocalDate.of(2023, Month.JANUARY,1),USER_ID),MEAL3,MEAL2,MEAL1);
    }

    @Test
    public void getBetweenWithNullDates() throws Exception{
        assertMatch(service.getBetweenDates(null,null,USER_ID),MEALS);
    }





}