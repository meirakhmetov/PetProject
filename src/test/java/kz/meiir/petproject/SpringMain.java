package kz.meiir.petproject;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.MealTo;
import kz.meiir.petproject.web.meal.MealRestController;
import kz.meiir.petproject.web.user.AdminRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SpringMain {
    public static void main(String[] args){
        //java 7 automatic resource management
        try(GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()){
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(),Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/inmemory.xml");
            appCtx.refresh();


            System.out.println("Bean definition names: "+ Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.getAll();
            System.out.println();

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
