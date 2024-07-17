package ca.lambtoncollege.fsdm.s24.auction.servlet;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.service.AuctionService;
import ca.lambtoncollege.fsdm.s24.auction.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;


@MultipartConfig(
        /*
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024,       // 1MB
        maxRequestSize = 1024 * 1024     // 1MB
        */
)
@WebServlet(name = "createAuctionServlet", value = "/auction/create")
public class CreateAuctionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var user = AuthService.authenticate(req);

            if (user == null) {
                throw new Exception("Not logged in");
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/account/sign-in");
            return;
        }

        req.getRequestDispatcher("/auction/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var title = req.getParameter("title");
        var description = req.getParameter("description");
        var minBid = req.getParameter("minBid");
        var endDate = req.getParameter("endDate");
        Part imagePart = req.getPart("auctionImage");
        try {
            var user = AuthService.authenticate(req);
            var auction = AuctionService.createAuction(title, description, minBid, endDate, imagePart, user, req);

            resp.sendRedirect(req.getContextPath() + "/auction/details?id=" + auction.getId());
        } catch (Exception e) {
            var errors = e instanceof ValidationException ? ((ValidationException) e).errors : new String[]{e.getMessage()};

            req.setAttribute("errors", errors);

            req.setAttribute("title", title);
            req.setAttribute("description", description);
            req.setAttribute("minBid", minBid);
            req.setAttribute("endDate", endDate);

            // Set uploadedImage attribute to retain in the form
            HttpSession session = req.getSession();
            String uploadedImage = (String) session.getAttribute("uploadedImage");
            req.setAttribute("auctionImage", uploadedImage);

            req.getRequestDispatcher("/auction/create.jsp").forward(req, resp);
        }
    }
}
