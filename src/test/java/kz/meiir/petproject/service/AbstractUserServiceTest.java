package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.JpaUtil;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;


import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static kz.meiir.petproject.UserTestData.*;


/**
 * @author Meiir Akhmetov on 18.01.2023
 */

public abstract class AbstractUserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception{
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception{
        User newUser = getNew();
        User created = service.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created,newUser);
        assertMatch(service.get(newId),newUser);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null,"Duplicate","user@ok.kz","password", Role.ROLE_USER));
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(USER_ID);
        service.get(USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception{
        service.delete(1);
    }

    @Test
    public void get() throws Exception{
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception{
        service.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@ok.kz");
        assertMatch(user, ADMIN);
    }

    @Test
    public void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all,ADMIN, USER);
    }
}