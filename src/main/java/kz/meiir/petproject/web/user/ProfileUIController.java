package kz.meiir.petproject.web.user;

import kz.meiir.petproject.AuthorizedUser;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.web.SecurityUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import static kz.meiir.petproject.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

/**
 * @author Meiir Akhmetov on 13.03.2023
 */
@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    @GetMapping
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser){
        model.addAttribute("userTo", authUser.getUserTo());
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status,@AuthenticationPrincipal AuthorizedUser authUser){
        if(result.hasErrors()){
            return "profile";
        }
        super.update(userTo, authUser.getId());
        authUser.update(userTo);
        status.setComplete();
        return "redirect:/meals";

    }

    @GetMapping("/register")
    public String register(ModelMap model){
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("register", true);
            return "profile";
        }
        super.create(userTo);
        status.setComplete();
        return "redirect:/login?message=app.registered&username=" + userTo.getEmail();


    }
}
