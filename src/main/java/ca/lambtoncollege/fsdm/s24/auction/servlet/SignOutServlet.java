package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "signOutServlet", value = "/account/sign-out")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AuthService.signOut(req);
        } catch (Exception e) {
            // ignore
        }

        resp.sendRedirect(req.getContextPath() + "/account/sign-in");
    }
}
