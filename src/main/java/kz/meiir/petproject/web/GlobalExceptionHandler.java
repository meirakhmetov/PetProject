package kz.meiir.petproject.web;

import kz.meiir.petproject.AuthorizedUser;
import kz.meiir.petproject.util.ValidationUtil;
import kz.meiir.petproject.util.exception.ErrorInfo;
import kz.meiir.petproject.util.exception.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Meiir Akhmetov on 14.03.2023
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger Log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView wrongRequest(HttpServletRequest req, NoHandlerFoundException e) throws Exception{
        return logAndExceptionView(req, e, false, ErrorType.WRONG_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaltErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Log.error("Exception at request " + req.getRequestURI(), e);
        return logAndExceptionView(req, e, true, ErrorType.APP_ERROR);
    }

    private ModelAndView  logAndExceptionView(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType){
        Throwable rootCause = ValidationUtil.logAndGetRootCause(Log, req, e, logException, errorType);

        HttpStatus httpStatus = errorType.getStatus();
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message", ValidationUtil.getMessage(rootCause),
                        "typeMessage", messageUtil.getMessage(errorType.getErrorCode()),
                        "status", httpStatus));
        mav.setStatus(httpStatus);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if(authorizedUser !=null){
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
