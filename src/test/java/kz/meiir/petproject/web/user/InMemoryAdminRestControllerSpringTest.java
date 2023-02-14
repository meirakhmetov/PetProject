package kz.meiir.petproject.web.user;

import kz.meiir.petproject.repository.inmemory.InMemoryUserRepository;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;


import static kz.meiir.petproject.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() throws Exception{
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
