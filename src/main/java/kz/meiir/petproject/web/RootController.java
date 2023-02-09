package kz.meiir.petproject.web;

import kz.meiir.petproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meiir Akhmetov on 09.02.2023
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request){
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }
}
