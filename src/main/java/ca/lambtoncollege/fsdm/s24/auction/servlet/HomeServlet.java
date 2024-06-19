package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "homeServlet", value = "/")
public class HomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            var user = AuthService.authenticate(req);

            if (user == null) {
                throw new Exception("Not logged in");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/account/sign-in");
            return;
        }

        var dispatch = req.getRequestDispatcher("/home.jsp");
        dispatch.forward(req, resp);
    }
}