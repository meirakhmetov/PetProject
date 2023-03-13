package kz.meiir.petproject;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static kz.meiir.petproject.TestUtil.readFromJsonMvcResult;
import static kz.meiir.petproject.TestUtil.readListFromJsonMvcResult;
import static kz.meiir.petproject.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ+1;

    public static final User USER = new User(USER_ID, "User", "user@ok.kz", "password",2005, Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID,"Admin","admin@ok.kz","admin",1900, Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew(){
        return new User(null, "New","new@ok.kz","newPass",1555,false , new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated(){
        User updated = new User(USER);
        updated.setName("UpdatesName");
        updated.setCaloriesPerDay(330);
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        return updated;
    }

   public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class,"registered", "meals");
}
