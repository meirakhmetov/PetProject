package kz.meiir.petproject;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;

import java.util.Arrays;
import java.util.List;

import static kz.meiir.petproject.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ+1;

    public static final User USER = new User(USER_ID, "User", "user@ok.kz", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID,"Admin","admin@ok.kz","admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected){
        assertThat(actual).isEqualToIgnoringGivenFields(expected,"registered","roles");
    }

    public static void assertMatch(Iterable<User> actual, User... expected){
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered","roles").isEqualTo(expected);
    }
}
