package kz.meiir.petproject.util;

import kz.meiir.petproject.util.exception.ErrorType;
import kz.meiir.petproject.util.exception.IllegalRequestDataException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import kz.meiir.petproject.HasId;
import kz.meiir.petproject.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id){
        return checkNotFound(object, "id="+id);
    }

    public static void checkNotFoundWithId(boolean found, int id){
        checkNotFound(found,"id="+id);
    }

    public static <T> T checkNotFound(T object, String msg){
        checkNotFound(object!=null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg){
        if(!found){
            throw new NotFoundException(arg);
        }
    }

    public static void checkNew(HasId bean){
        if(!bean.isNew()){
            throw new IllegalRequestDataException(bean + "must be new (id=null");
        }
    }

    public static void assureIdConsistent(HasId bean, int id){
        // conservative when you reply, but  accept liberally (http://stackoverflow.com/a/32728226/548473)
        if(bean.isNew()){
            bean.setId(id);
        }else if(bean.id() != id){
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    // http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t){
        Throwable result = t;
        Throwable cause;

        while(null!=(cause = result.getCause())&&(result!=cause)){
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e){
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    private static final Validator validator;

    static{
        //From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean){
        //https://alexkosarev.name/2018/07/30/bean-validation-api/
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    public static Throwable logAndGetRootCause(Logger Log, HttpServletRequest req, Exception e, boolean logException, ErrorType errorType){
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if(logException){
            Log.error(errorType + " at request" + req.getRequestURI(), rootCause);
        }else{
            Log.warn("{} at request {}: {}", errorType, req.getRequestURI(), rootCause.toString());
        }
        return rootCause;
    }
}
