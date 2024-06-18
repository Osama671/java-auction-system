package ca.lambtoncollege.fsdm.s24.auction.service;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.model.Session;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.repository.SessionRepository;
import ca.lambtoncollege.fsdm.s24.auction.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    public static User createUser(String email, String name, String password) throws Exception {
        var errors = new ArrayList<String>();

        if (email == null || !isValidEmail(email)) {
            errors.add("Invalid email address");
        }

        if (password == null || password.length() < 8) {
            errors.add("Password should be min 8 characters");
        }

        if (name == null || name.isEmpty()) {
            errors.add("Name cannot be empty");
        }

        if (email != null && UserRepository.findUser(email) != null) {
            errors.add("This email is already taken. Please use a different one");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        var hashedPassword = hashPassword(password);

        UserRepository.addUser(email, name, hashedPassword);

        return UserRepository.findUser(email);
    }

    public static Session signIn(String email, String password) throws SQLException, ValidationException, NoSuchAlgorithmException {
        var errors = new ArrayList<String>();

        if (email == null || email.isEmpty()) {
            errors.add("Email cannot be empty");
        }

        if (password == null) {
            errors.add("Password cannot be empty");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        var user = UserRepository.findUser(email);

        if (user == null || !verifyPassword(password, user.getPassword())) {
            errors.add("Invalid username/password");
            throw new ValidationException(errors);
        }

        return SessionRepository.createSession(user);
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            return saltBase64 + ":" + hashedPasswordBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hashPassword(password, salt);
    }

    public static boolean verifyPassword(String password, String storedPassword) throws NoSuchAlgorithmException {
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            return false;
        }

        String saltBase64 = parts[0];

        byte[] salt = Base64.getDecoder().decode(saltBase64);

        return storedPassword.equals(hashPassword(password, salt));
    }
}
