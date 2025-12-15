package com.hotel.service;

import com.hotel.entities.Booking;
import com.hotel.entities.Room;
import com.hotel.entities.RoomType;
import com.hotel.entities.User;
import com.hotel.exception.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Service {

    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    private int bookingIdCounter = 1;

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        if (roomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        if (roomPricePerNight < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative");
        }

        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);

        rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
    }

    public void setUser(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        User existingUser = findUserById(userId);

        if (existingUser != null) {
            existingUser.setBalance(balance);
        } else {
            users.add(new User(userId, balance));
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut)
            throws InvalidDateException, UserNotFoundException, RoomNotFoundException,
            RoomNotAvailableException, InsufficientBalanceException {

        if (checkIn == null || checkOut == null) {
            throw new InvalidDateException("Check-in and check-out dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new InvalidDateException("Check-in date must be before check-out date");
        }

        User user = findUserById(userId);
        Room room = findRoomByNumber(roomNumber);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        if (room == null) {
            throw new RoomNotFoundException(roomNumber);
        }

        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            throw new RoomNotAvailableException(roomNumber);
        }

        int nights = calculateNights(checkIn, checkOut);
        int totalPrice = nights * room.getPricePerNight();

        if (!user.hasSufficientBalance(totalPrice)) {
            throw new InsufficientBalanceException(totalPrice, user.getBalance());
        }

        Booking booking = new Booking(bookingIdCounter++, user, room, checkIn, checkOut);
        bookings.add(booking);
        user.setBalance(user.getBalance() - totalPrice);
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (datesOverlap(checkIn, checkOut, booking.getCheckInDate(), booking.getCheckOutDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
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

    public void printAll() {
        System.out.println("========== ROOMS (newest to oldest) ==========");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        System.out.println("\n========== BOOKINGS (newest to oldest) ==========");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
    }

    public void printAllUsers() {
        System.out.println("========== USERS (newest to oldest) ==========");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public ArrayList<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
}
