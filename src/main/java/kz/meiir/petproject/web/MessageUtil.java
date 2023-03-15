package kz.meiir.petproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Meiir Akhmetov on 15.03.2023
 */
@Component
public class MessageUtil {

    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Locale locale){
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code){
        return getMessage(code, LocaleContextHolder.getLocale());
    }
}
