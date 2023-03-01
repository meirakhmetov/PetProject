package kz.meiir.petproject;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;

/**
 * @author Meiir Akhmetov on 28.02.2023
 */
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user){
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId(){
        return userTo.id();
    }

    public void update(UserTo newTo){
        userTo = newTo;
    }

    public UserTo getUserTo(){
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
