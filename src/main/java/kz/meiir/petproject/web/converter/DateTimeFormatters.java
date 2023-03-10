package kz.meiir.petproject.web.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static kz.meiir.petproject.util.DateTimeUtil.parseLocalDate;
import static kz.meiir.petproject.util.DateTimeUtil.parseLocalTime;

/**
 * @author Meiir Akhmetov on 21.02.2023
 */
public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate>{

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime>{

        @Override
        public LocalTime parse(String text, Locale locale) {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
