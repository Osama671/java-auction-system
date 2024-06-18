package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "signInServlet", value = "/account/sign-in")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/account/sign-in.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("email");
        var password = req.getParameter("password");

        try {
            var user = UserService.signIn(email, password);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors : new ArrayList<String>().add(e.getMessage());

            req.setAttribute("errors", errors);
            req.setAttribute("email", email);
            req.setAttribute("password", password);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/account/sign-in.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
