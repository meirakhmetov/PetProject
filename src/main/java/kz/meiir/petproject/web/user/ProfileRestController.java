package kz.meiir.petproject.web.user;

import kz.meiir.petproject.model.User;

import static kz.meiir.petproject.web.SecurityUtil.authUserId;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class ProfileRestController extends AbstractUserController {

    public User get(){
        return super.get(authUserId());
    }

    public void delete(){
        super.delete(authUserId());
    }

    public void update(User user){
        super.update(user, authUserId());
    }
}
