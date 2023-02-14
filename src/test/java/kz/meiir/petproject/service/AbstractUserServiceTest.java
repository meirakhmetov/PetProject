package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;


import java.util.List;


import static kz.meiir.petproject.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author Meiir Akhmetov on 18.01.2023
 */

public abstract class AbstractUserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception{
        cacheManager.getCache("users").clear();
    }

    @Test
    void create() throws Exception{
        User newUser = getNew();
        User created = service.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created,newUser);
        assertMatch(service.get(newId),newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class,() ->
                service.create(new User(null,"Duplicate","user@ok.kz","password", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class,() ->
                service.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception{
        assertThrows(NotFoundException.class,() ->
                service.delete(1));
    }

    @Test
    public void get() throws Exception{
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception{
        assertThrows(NotFoundException.class,() ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@ok.kz");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all,ADMIN, USER);
    }
}