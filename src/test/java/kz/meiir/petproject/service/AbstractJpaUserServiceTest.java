package kz.meiir.petproject.service;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.JpaUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

/**
 * @author Meiir Akhmetov on 13.02.2023
 */
abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest{

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    @Override
    public void sepUp() throws Exception{
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void createWithException() throws Exception{
        validateRootCause(()-> service.create(new User(null," ","mail@ok.kz","password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new User(null,"User"," ","password",Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new User(null,"User","mail@ok.kz"," ",Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new User(null,"User","mail@ok.kz","password",9,true,new Date(), Set.of())), ConstraintViolationException.class);
        validateRootCause(()-> service.create(new User(null,"User","mail@ok.kz","password",10001,true,new Date(), Set.of())), ConstraintViolationException.class);
    }
}
