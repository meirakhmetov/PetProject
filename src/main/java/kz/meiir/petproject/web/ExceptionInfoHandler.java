package kz.meiir.petproject.web;

import kz.meiir.petproject.util.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import kz.meiir.petproject.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

import static kz.meiir.petproject.util.exception.ErrorType.*;

/**
 * @author Meiir Akhmetov on 14.03.2023
 */
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger Log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    public static final String EXCEPTION_DUPLICATE_DATETIME = "exception.meal.duplicateDateTime";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "meals_unique_user_datetime_idx",EXCEPTION_DUPLICATE_DATETIME);

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationError(HttpServletRequest req, ApplicationException appEx){
        ErrorInfo errorInfo = logAndGetErrorInfo(req, appEx, false, appEx.getType(), messageUtil.getMessage(appEx));
        return ResponseEntity.status(appEx.getType().getStatus()).body(errorInfo);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e){
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if(rootMsg != null){
            String lowelCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String,String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowelCaseMsg.contains(it.getKey()))
                    .findAny();
            if(entry.isPresent()){
                return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, messageUtil.getMessage(entry.get().getValue()));
            }
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) //422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e){
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> messageUtil.getMessage(fe))
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
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
    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String...details){
        Throwable rootCause = ValidationUtil.logAndGetRootCause(Log, req, e, logException, errorType);
        return new ErrorInfo(req.getRequestURI(), errorType,
                messageUtil.getMessage(errorType.getErrorCode()),
                details.length !=0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }
}
