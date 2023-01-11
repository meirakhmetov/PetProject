package kz.meiir.petproject.web;

import static kz.meiir.petproject.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SecurityUtil {

    private static int id=1;

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
