package siit.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    public static final String LOGIN_JSP_PATH = "WEB-INF/login.jsp";
    private int numberOfLoginAttempts = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var username = req.getParameter("username");
        var password = req.getParameter("password");

        if (username.equals(password)) {
            // redirect la dashboard
            HttpSession session = req.getSession();
            session.setAttribute("logged_user", username);
            resp.sendRedirect("/customer");
        } else {
            // create an error message and assign it to the error variable
            numberOfLoginAttempts++;
            String error1 = "User and password do not match! Number of attempts: " + numberOfLoginAttempts;
            req.setAttribute("error", error1);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_JSP_PATH);
            requestDispatcher.forward(req, resp);
        }

    }
}
