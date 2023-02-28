package kz.meiir.petproject.web;

import kz.meiir.petproject.service.MealService;
import kz.meiir.petproject.util.MealsUtil;
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
    private MealService mealService;

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(){
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request){
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getMeals(Model model){
        model.addAttribute("meals",
                MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}
