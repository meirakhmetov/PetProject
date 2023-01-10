package kz.meiir.petproject.util;

import org.springframework.lang.Nullable;

import java.time.LocalTime;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
public class Util {
    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start,@Nullable T end){
        return value.compareTo(start) >=0 && value.compareTo(end) <=0;
    }
}