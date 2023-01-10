package kz.meiir.petproject.web.user;

import kz.meiir.petproject.model.User;

import java.util.Collections;
import java.util.List;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class AdminRestController extends AbstractUserController{

    @Override
    public List<User> getAll(){
        return super.getAll();
    }

    @Override
    public User get(int id){
        return super.get(id);
    }

    @Override
    public User create(User user){
        return super.create(user);
    }

    @Override
    public void delete(int id){
        super.delete(id);
    }

    @Override
    public void update(User user, int id){
        super.update(user,id);
    }

    @Override
    public User getByEmail(String email){
        return super.getByEmail(email);
    }
}
