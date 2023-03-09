package kz.meiir.petproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Meiir Akhmetov on 09.02.2023
 */
@Controller
public class RootController {

    @GetMapping("/")
    public String root(){
        return "redirect:meals";
    }

    @GetMapping("/users")
    public String getUsers(){
        return "users";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping("/meals")
    public String getMeals(){
        return "meals";
    }
}
