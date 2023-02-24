package kz.meiir.petproject.util;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.to.UserTo;

/**
 * @author Meiir Akhmetov on 24.02.2023
 */
public class UserUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static User creteNewFromTo(UserTo userTo){
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.ROLE_USER);
    }
}
