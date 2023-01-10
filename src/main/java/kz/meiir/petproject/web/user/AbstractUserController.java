package kz.meiir.petproject.web.user;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.assureIdConsistent;


/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public abstract class AbstractUserController {
    protected final Logger Log = LoggerFactory.getLogger(getClass());

    private UserService service;

    public List<User> getAll(){
        Log.info("getAll");
        return service.getAll();
    }

    public User get(int id){
        Log.info("get {}",id);
        return service.get(id);
    }

    public User create(User user){
        Log.info("create {}",user);
        return service.create(user);
    }

    public void delete(int id){
        Log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id){
        Log.info("update {} with id={}",user,id);
        assureIdConsistent(user, id);
    }

    public User getByEmail(String email){
        Log.info("getByEmail {}",email);
        return service.getByEmail(email);
    }
}
