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
        userCookie.setPath("/");
        return userCookie;
    }

    public static User authenticate(HttpServletRequest req) throws SQLException {
        var sessionId = getSessionId(req);

        if (sessionId == null) {
            return null;
        }

        var session = UserService.authenticate(sessionId);

        return session.getUser();
    }

    public static void signOut(HttpServletRequest req) throws SQLException {
        var sessionId = getSessionId(req);

        if (sessionId == null) {
            return;
        }

        UserService.signOut(sessionId);
    }

    private static String getSessionId(HttpServletRequest req) {
        var sessionCookie = Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("session")).findFirst().orElse(null);

        if (sessionCookie == null) {
            return null;
        }

        return sessionCookie.getValue();
    }
}
