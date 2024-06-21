package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import ca.lambtoncollege.fsdm.s24.auction.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "registrationServlet", value = "/account/register")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var user = AuthService.authenticate(req);
            if (user != null) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        } catch (Exception e) {
            // ignore
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/account/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var name = req.getParameter("name");
        var password = req.getParameter("password");

        try {
            UserService.createUser(email, name, password);

            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors : new String[]{e.getMessage()};

            req.setAttribute("errors", errors);
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("name", name);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/account/register.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
