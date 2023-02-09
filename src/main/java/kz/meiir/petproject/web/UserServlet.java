package kz.meiir.petproject.web;

import kz.meiir.petproject.web.user.AdminRestController;
import org.slf4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Meiir Akhmetov on 05.01.2023
 */
public class UserServlet extends HttpServlet {
    private static final Logger Log  = getLogger(UserServlet.class);

    private AdminRestController adminController;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        adminController = springContext.getBean(AdminRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Log.debug("forward to users");
        request.setAttribute("users", adminController.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request,response);
    }

}
