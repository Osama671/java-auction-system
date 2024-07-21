package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.repository.AuctionRepository;
import ca.lambtoncollege.fsdm.s24.auction.repository.BidRepository;
import ca.lambtoncollege.fsdm.s24.auction.service.AuctionService;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "updateHighestBidderServlet", value = "/auction/updateBid")
public class UpdateHighestBidderServlet extends HttpServlet {
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

        try {
            var auctionId = Integer.parseInt(req.getParameter("auctionId"));
            var userId = Integer.parseInt(req.getParameter("userId"));
            var userBid = Integer.parseInt(req.getParameter("bid"));
            var currentBid = (BidRepository.getHighestBid(auctionId) == null) ? Double.NEGATIVE_INFINITY : BidRepository.getHighestBid(auctionId).getAmount();
            var minBid = AuctionRepository.getAuctionById(auctionId).getMinBid() / 100F;

            if(userBid > currentBid && userBid > minBid){
                 BidRepository.addBid(auctionId, userId, userBid);
                 resp.sendRedirect(req.getContextPath() + "/auction/details?id=" + auctionId) ;
            }
            // Unsure what to do if user bid lower or equal to the highest bid
            // Would like to talke about it later thanks uwu :3
            else{
                resp.sendRedirect(req.getContextPath() + "/auction/details?id=" + auctionId);
            }

        } catch (Exception e) {
            System.out.println(e);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

}
