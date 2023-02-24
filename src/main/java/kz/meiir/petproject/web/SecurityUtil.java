package kz.meiir.petproject.web;

import kz.meiir.petproject.model.AbstractBaseEntity;

import static kz.meiir.petproject.util.UserUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SecurityUtil {

    private static int id= AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId(){
        return id;
    }

    public static void setAuthUserId(int id){
        SecurityUtil.id = id;
    }

    public static int authUserCaloriesPerDay(){
        return DEFAULT_CALORIES_PER_DAY;
    }
}
