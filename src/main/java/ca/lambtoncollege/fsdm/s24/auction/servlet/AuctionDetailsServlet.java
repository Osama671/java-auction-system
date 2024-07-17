package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.model.Bid;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.service.AuctionService;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import ca.lambtoncollege.fsdm.s24.auction.service.UserService;
import jakarta.servlet.RequestDispatcher;
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
            Bid highestBid = AuctionService.getHighestBid(auctionId);
            req.setAttribute("highestBid", highestBid);
            req.setAttribute("auction", auction);
            req.setAttribute("userId", user.getId());
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors
                    : new String[] { e.getMessage() };

            req.setAttribute("errors", errors);
        }

        var dispatch = req.getRequestDispatcher("/auction/auction-details.jsp");
        dispatch.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        var auctionId = req.getParameter("auctionId");
        var bid = req.getParameter("bid");

        try {
            AuctionService.addBid(Integer.parseInt(auctionId), user.getId(), Integer.parseInt(bid) * 100);

            resp.sendRedirect(req.getContextPath() + "/auction/details?id=" + auctionId);
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors
                    : new String[] { e.getMessage() };
            System.out.println(errors.length);
            req.setAttribute("errors", errors);
            doGet(req, resp);
        }
    }
}
