package com.hotel.entities;

import java.util.Date;

public class User {
    private final int userId;
    private int balance;
    private final Date creationDate;

    public User(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.userId = userId;
        this.balance = balance;
        this.creationDate = new Date();
    }

    public int getUserId() { return userId; }
    public int getBalance() { return balance; }
    public Date getCreationDate() { return new Date(creationDate.getTime()); }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public boolean hasSufficientBalance(int amount) {
        return balance >= amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", balance=" + balance +
                '}';
    }
}