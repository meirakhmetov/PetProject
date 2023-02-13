package kz.meiir.petproject.web.user;

import kz.meiir.petproject.repository.inmemory.InMemoryUserRepository;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import static kz.meiir.petproject.UserTestData.USER_ID;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() throws Exception{
        repository.init();
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception{
        controller.delete(USER_ID);
        controller.get(USER_ID);

    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception{
        controller.delete(10);
    }

}
