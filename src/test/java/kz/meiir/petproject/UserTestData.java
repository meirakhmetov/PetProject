package kz.meiir.petproject;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;

/**
 * @author Meiir Akhmetov on 12.01.2023
 */
public class UserTestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final User USER = new User(USER_ID, "User", "user@ok.kz", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID,"Admin","admin@ok.kz","admin", Role.ROLE_ADMIN);
}
