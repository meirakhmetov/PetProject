package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;


import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;


import static kz.meiir.petproject.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Meiir Akhmetov on 18.01.2023
 */

public abstract class AbstractUserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Test
    void create() throws Exception{
        User newUser = getNew();
        User created = service.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created,newUser);
        USER_MATCHERS.assertMatch(service.get(newId),newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class,() ->
                service.create(new User(null,"Duplicate","user@ok.kz","newPass",2000, Role.ROLE_USER)));
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
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception{
        assertThrows(NotFoundException.class,() ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@ok.kz");
        USER_MATCHERS.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHERS.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHERS.assertMatch(all,ADMIN, USER);
    }

    @Test
    void createWithException() throws Exception{
        validateRootCause(() -> service.create(new User(null," ", "mail@ok.kz", "password",2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null,"User", "", "password",2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null,"User", "mail@ok.kz", " ",2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null,"User", "mail@ok.kz", "password",9,true,new Date(), Set.of())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null,"User", "mail@ok.kz", "password",100001,true,new Date(), Set.of())), ConstraintViolationException.class);
    }

    @Test
    void enable(){
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}