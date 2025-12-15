package com.hotel.exception;

public class RoomNotFoundException extends HotelReservationException {
    public RoomNotFoundException(int roomNumber) {
        super("Room not found: " + roomNumber);
    }
}
