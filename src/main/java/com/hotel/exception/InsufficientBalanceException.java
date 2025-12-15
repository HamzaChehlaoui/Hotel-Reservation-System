package com.hotel.exception;

public class InsufficientBalanceException extends HotelReservationException {
    public InsufficientBalanceException(int required, int available) {
        super("Insufficient balance. Required: " + required + ", Available: " + available);
    }
}
