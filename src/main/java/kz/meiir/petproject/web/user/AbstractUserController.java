package kz.meiir.petproject.web.user;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.assureIdConsistent;
import static kz.meiir.petproject.util.ValidationUtil.checkNew;


/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public abstract class AbstractUserController {
    protected final Logger Log = LoggerFactory.getLogger(getClass());

    public static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";

    @Autowired
    private UserService service;

    public List<User> getAll(){
        Log.info("getAll");
        return service.getAll();
    }

    public User get(int id){
        Log.info("get {}",id);
        return service.get(id);
    }

    public User create(UserTo userTo){
        Log.info("create from to {}", userTo);
        return create(UserUtil.creteNewFromTo(userTo));
    }

    public User create(User user){
        Log.info("create {}",user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id){
        Log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id){
        Log.info("update {} with id={}",user,id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id){
        Log.info("update {} with id={}", userTo,id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User getByEmail(String email){
        Log.info("getByEmail {}",email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled){
        Log.info(enabled ? "enable {}": "disable{}", id);
        service.enable(id, enabled);
    }
}
