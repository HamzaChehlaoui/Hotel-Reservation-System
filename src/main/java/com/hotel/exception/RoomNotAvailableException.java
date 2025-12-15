package com.hotel.exception;

public class RoomNotAvailableException extends HotelReservationException {
    public RoomNotAvailableException(int roomNumber) {
        super("Room " + roomNumber + " is not available for the specified period");
    }
}
