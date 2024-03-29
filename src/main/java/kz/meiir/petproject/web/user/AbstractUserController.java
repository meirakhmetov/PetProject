package kz.meiir.petproject.web.user;

import kz.meiir.petproject.Profiles;
import kz.meiir.petproject.model.AbstractBaseEntity;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;
import kz.meiir.petproject.util.exception.ModificationRestrictionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.assureIdConsistent;
import static kz.meiir.petproject.util.ValidationUtil.checkNew;


/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public abstract class AbstractUserController {
    protected final Logger Log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    private boolean modificationRestriction;

    @Autowired
    @SuppressWarnings("deprecation")
    public void setEnvironment(Environment environment){
        modificationRestriction = environment.acceptsProfiles(Profiles.HEROKU);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(emailValidator);
    }

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
        chekModificationAllowed(id);
        service.delete(id);
    }

    public void update(User user, int id){
        Log.info("update {} with id={}",user,id);
        assureIdConsistent(user, id);
        chekModificationAllowed(id);
        service.update(user);
    }

    public void update(UserTo userTo, int id){
        Log.info("update {} with id={}", userTo,id);
        assureIdConsistent(userTo, id);
        chekModificationAllowed(id);
        service.update(userTo);
    }

    public User getByEmail(String email){
        Log.info("getByEmail {}",email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled){
        Log.info(enabled ? "enable {}": "disable{}", id);
        chekModificationAllowed(id);
        service.enable(id, enabled);
    }

    private void chekModificationAllowed(int id){
        if (modificationRestriction && id < AbstractBaseEntity.START_SEQ + 2){
            throw new ModificationRestrictionException();
        }
    }
}
