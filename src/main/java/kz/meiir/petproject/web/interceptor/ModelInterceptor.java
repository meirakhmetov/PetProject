package kz.meiir.petproject.web.interceptor;

import kz.meiir.petproject.AuthorizedUser;
import kz.meiir.petproject.web.SecurityUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Meiir Akhmetov on 13.03.2023
 */

/**
 * This interceptor adds userTo to the model of every requests
 */
public class ModelInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && !modelAndView.isEmpty()){
            AuthorizedUser authorizedUser = SecurityUtil.safeGet();
            if(authorizedUser != null){
                modelAndView.getModelMap().addAttribute("userTo", authorizedUser.getUserTo());
            }
        }
    }
}
