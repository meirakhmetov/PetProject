package kz.meiir.petproject.util;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * @author Meiir Akhmetov on 24.02.2023
 */
public class UserUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static User creteNewFromTo(UserTo userTo){
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), userTo.getCaloriesPerDay(), Role.ROLE_USER);
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

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder){
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
