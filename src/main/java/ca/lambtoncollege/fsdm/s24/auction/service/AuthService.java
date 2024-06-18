package ca.lambtoncollege.fsdm.s24.auction.service;

import ca.lambtoncollege.fsdm.s24.auction.model.Session;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Arrays;

public class AuthService {
    public static Cookie createSessionCookie(Session session) {
        Cookie userCookie = new Cookie("session", session.getSessionId().toString());
        userCookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
        userCookie.setHttpOnly(true);
        return userCookie;
    }

    public static User authenticate(HttpServletRequest req) throws SQLException {
        var sessionCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("session"))
                .findFirst()
                .orElse(null);

        if (sessionCookie == null) {
            return null;
        }

        var sessionId = sessionCookie.getValue();
        var session = UserService.authenticate(sessionId);

        return session.getUser();
    }
}
