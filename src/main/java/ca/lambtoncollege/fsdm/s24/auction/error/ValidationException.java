package ca.lambtoncollege.fsdm.s24.auction.error;

import java.util.List;

public class ValidationException extends Exception {
    public final String[] errors;

    public ValidationException(List<String> errors) {
        super("Multiple validation errors");
        this.errors = errors.toArray(String[]::new);
    }
}
