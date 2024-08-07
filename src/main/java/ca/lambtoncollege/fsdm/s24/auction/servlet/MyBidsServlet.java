package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.model.Auction;
import ca.lambtoncollege.fsdm.s24.auction.model.Bid;
import ca.lambtoncollege.fsdm.s24.auction.service.AuctionService;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import ca.lambtoncollege.fsdm.s24.auction.service.BidService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MyBidsServlet", value = "/auction/my-bids")
public class MyBidsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            var user = AuthService.authenticate(req);

            if (user == null) {
                throw new Exception("Not logged in");
            }

            req.setAttribute("userId", user.getId());

            ArrayList<Auction> auctions;
            ArrayList<Bid> bids;
            auctions = AuctionService.getAuctions();
            bids = BidService.getMyHighestBids(user.getId());

            req.setAttribute("auctions", auctions);
            req.setAttribute("bids", bids);

            var dispatch = req.getRequestDispatcher("/auction/my-bids.jsp");
            dispatch.forward(req, resp);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/account/sign-in");
        }
    }
}