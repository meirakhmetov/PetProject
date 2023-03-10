package kz.meiir.petproject;

import kz.meiir.petproject.to.MealTo;
import kz.meiir.petproject.web.meal.MealRestController;
import kz.meiir.petproject.web.user.AdminRestController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static kz.meiir.petproject.TestUtil.mockAuthorize;
import static kz.meiir.petproject.UserTestData.USER;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SpringMain {
    public static void main(String[] args){
        //java 7 automatic resource management
        try(ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")){
            System.out.println("Bean definition names: "+ Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.getAll();
            System.out.println();

            mockAuthorize(USER);

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2023, Month.JANUARY,1), LocalTime.of(7,0),
                            LocalDate.of(2023, Month.JANUARY,2),LocalTime.of(11,0)
                    );
            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
