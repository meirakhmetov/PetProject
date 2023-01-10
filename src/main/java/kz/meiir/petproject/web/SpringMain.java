package kz.meiir.petproject.web;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.web.user.AdminRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

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
        }
    }
}
