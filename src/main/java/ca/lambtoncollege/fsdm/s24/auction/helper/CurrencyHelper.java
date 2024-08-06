package ca.lambtoncollege.fsdm.s24.auction.helper;

public class CurrencyHelper {
    public static String asDollars(long cents) {
        long dollars = cents / 100;
        long remainingCents = cents % 100;

        return String.format("$%d.%02d", dollars, remainingCents);
    }
}
