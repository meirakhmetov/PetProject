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

    @ExceptionHandler(Exception.class)
    public ModelAndView defaltErrorHandler(HttpServletRequest reg, Exception e) throws Exception{
        Log.error("Exception at request " + reg.getRequestURI(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message", ValidationUtil.getMessage(rootCause),
                        "typeMessage", messageUtil.getMessage(ErrorType.APP_ERROR.getErrorCode()),
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
