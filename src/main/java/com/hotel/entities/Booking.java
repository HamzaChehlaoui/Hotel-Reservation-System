package com.hotel.entities;

import java.util.Calendar;
import java.util.Date;

public class Booking {

    private final int bookingId;
    private final int userId;
    private final int roomNumber;

    private final RoomType bookedRoomType;
    private final int bookedPricePerNight;
    private final int userBalanceAtBooking;

    private final Date checkInDate;
    private final Date checkOutDate;

    private final int totalPrice;
    private final int numberOfNights;
    private final Date bookingDate;

    public Booking(int bookingId, User user, Room room,
            Date checkInDate, Date checkOutDate) {

        validateInputs(user, room, checkInDate, checkOutDate);

        this.bookingId = bookingId;
        this.userId = user.getUserId();
        this.roomNumber = room.getRoomNumber();

        this.bookedRoomType = room.getRoomType();
        this.bookedPricePerNight = room.getPricePerNight();
        this.userBalanceAtBooking = user.getBalance();

        this.checkInDate = new Date(checkInDate.getTime());
        this.checkOutDate = new Date(checkOutDate.getTime());

        this.numberOfNights = calculateNights(checkInDate, checkOutDate);
        this.totalPrice = this.numberOfNights * this.bookedPricePerNight;
        this.bookingDate = new Date();
    }

    private void validateInputs(User user, Room room,
            Date checkIn, Date checkOut) {
        if (user == null || room == null) {
            throw new IllegalArgumentException("User and Room cannot be null");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Check-in must be before check-out");
        }
    }

    private int calculateNights(Date checkIn, Date checkOut) {
        Calendar start = Calendar.getInstance();
        start.setTime(checkIn);
        setZeroTime(start);

        Calendar end = Calendar.getInstance();
        end.setTime(checkOut);
        setZeroTime(end);

        long diff = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    private void setZeroTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getBookedRoomType() {
        return bookedRoomType;
    }

    public int getBookedPricePerNight() {
        return bookedPricePerNight;
    }

    public int getUserBalanceAtBooking() {
        return userBalanceAtBooking;
    }

    public Date getCheckInDate() {
        return new Date(checkInDate.getTime());
    }

    public Date getCheckOutDate() {
        return new Date(checkOutDate.getTime());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public Date getBookingDate() {
        return new Date(bookingDate.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Booking booking = (Booking) obj;
        return bookingId == booking.bookingId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(bookingId);
    }

    @Override
    public String toString() {
        return String.format(
                "Booking{id=%d, userId=%d, roomNumber=%d, type=%s, price=%d, nights=%d, total=%d, userBalanceSnapshot=%d}",
                bookingId, userId, roomNumber, bookedRoomType, bookedPricePerNight,
                numberOfNights, totalPrice, userBalanceAtBooking);
    }
}