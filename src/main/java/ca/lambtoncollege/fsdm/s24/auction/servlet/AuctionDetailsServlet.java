package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.service.AuctionService;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "auctionDetailsServet", value = "/auction/details")
public class AuctionDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;

        try {
            user = AuthService.authenticate(req);

            if (user == null) {
                throw new Exception("Not logged in");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/account/sign-in");
            return;
        }

        try {
            var auctionId = Integer.parseInt(req.getParameter("id"));
            var auction = AuctionService.getAuction(auctionId);

            req.setAttribute("auction", auction);
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors : new String[]{e.getMessage()};

            req.setAttribute("errors", errors);
        }

        var dispatch = req.getRequestDispatcher("/auction/auction-details.jsp");
        dispatch.forward(req, resp);
    }
}
