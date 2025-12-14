package com.hotel.entities;

public enum RoomType {
    STANDARD_SUITE,
    JUNIOR_SUITE,
    MASTER_SUITE;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
