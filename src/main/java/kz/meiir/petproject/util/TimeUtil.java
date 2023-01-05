package kz.meiir.petproject.util;

import java.time.LocalTime;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime){
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <=0;
    }
}
