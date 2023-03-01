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

    public static UserTo asTo(User user){
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay());
    }

    public static User updateFromTo(User user, UserTo userTo){
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setCaloriesPerDay(userTo.getCaloriesPerDay());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
