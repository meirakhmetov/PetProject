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
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")){
            System.out.println("Bean definition names: "+ Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
            adminRestController.create(new User(null, "userName","email@mail.ru", "password", Role.ROLE_ADMIN));
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