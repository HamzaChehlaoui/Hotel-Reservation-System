package com.hotel.entities;

import java.util.Date;

public class Room {

    private final int roomNumber;
    private final RoomType roomType;
    private final int pricePerNight;
    private final Date creationDate;

    public Room(int roomNumber , RoomType roomType , int pricePerNight ) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.creationDate = new Date();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public Date getCreationDate(){
        return new Date(creationDate.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(roomNumber);
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + roomNumber +
                ", type=" + roomType +
                ", price=" + pricePerNight +
                '}';
    }
}
