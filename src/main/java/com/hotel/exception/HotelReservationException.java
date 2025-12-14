package com.hotel.exception;

public abstract class HotelReservationException extends Exception {
    public HotelReservationException(String message) {
        super(message);
    }
}
