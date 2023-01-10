package kz.meiir.petproject.web;

import kz.meiir.petproject.repository.UserRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SpringMain {
    public static void main(String[] args){
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: "+ Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserRepository userRepository = appCtx.getBean(UserRepository.class);
        userRepository.getAll();
        appCtx.close();

    }
}
