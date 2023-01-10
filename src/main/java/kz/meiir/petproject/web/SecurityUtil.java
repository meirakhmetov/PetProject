package kz.meiir.petproject.web;

import static kz.meiir.petproject.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class SecurityUtil {

    public static int authUserId(){
        return 1;
    }

    public static int authUserCaloriesPerDay(){
        return DEFAULT_CALORIES_PER_DAY;
    }
}
