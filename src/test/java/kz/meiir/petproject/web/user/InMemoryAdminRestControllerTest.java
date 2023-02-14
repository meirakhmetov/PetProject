package kz.meiir.petproject.web.user;

import kz.meiir.petproject.repository.inmemory.InMemoryUserRepository;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static kz.meiir.petproject.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
class InMemoryAdminRestControllerTest {
    private static final Logger Log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeAll
    static void beforeClass(){
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/inmemory.xml");
        Log.info("\n{}\n" + Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminRestController.class);
    }

    @AfterAll
    static void afterClass(){
        // May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
        // http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @BeforeEach
    void setUp() throws Exception{
        //re-initialize
        InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
        repository.init();
    }

    @Test
    void delete() throws Exception{
        controller.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception{
        assertThrows(NotFoundException.class, () -> controller.delete(10));
    }
}
