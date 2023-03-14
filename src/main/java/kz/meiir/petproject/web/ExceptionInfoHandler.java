package kz.meiir.petproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import kz.meiir.petproject.util.ValidationUtil;
import kz.meiir.petproject.util.exception.ErrorInfo;
import kz.meiir.petproject.util.exception.ErrorType;
import kz.meiir.petproject.util.exception.IllegalRequestDataException;
import kz.meiir.petproject.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static kz.meiir.petproject.util.exception.ErrorType.*;

/**
 * @author Meiir Akhmetov on 14.03.2023
 */
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger Log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e){
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e){
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataEroor(HttpServletRequest req, Exception e){
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e){
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    // https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType){
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if(logException){
            Log.error(errorType + " at request" + req.getRequestURI(), rootCause);
        }else{
            Log.warn("{} at request {}: {}", errorType, req.getRequestURI(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURI(), errorType, rootCause.toString());
    }
}
