package kz.meiir.petproject.web;

import org.slf4j.Logger;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Log.debug("redirect tot users");
//        request.getRequestDispatcher("/users.jsp").forward(request,response);
        response.sendRedirect("users.jsp");
    }

}
