package com.hotel.exception;

public class UserNotFoundException extends HotelReservationException {
    public UserNotFoundException(int userId) {
        super("User not found: " + userId);
    }
}
