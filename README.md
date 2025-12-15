# 🏨 Hotel Reservation System

A simplified hotel reservation management system built with Java, implementing core booking functionality with robust exception handling and data integrity.

![hotel reservation](images/imageH.png)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technical Requirements](#technical-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Design Decisions](#design-decisions)
- [Test Cases](#test-cases)
- [Screenshots](#screenshots)
- [Technologies](#technologies)

## 🎯 Overview

This project is a technical test implementation for a Hotel Reservation System. It manages three main entities: **Users**, **Rooms**, and **Bookings**, providing a complete booking workflow with balance validation, room availability checking, and historical data preservation.

## ✨ Features

### Core Functionality
- ✅ **User Management**: Create and manage users with balance tracking
- ✅ **Room Management**: Define rooms with different types and pricing
- ✅ **Booking System**: Reserve rooms for specific periods with automatic validation
- ✅ **Balance Management**: Automatic balance deduction upon successful booking
- ✅ **Availability Checking**: Prevents double-booking with date overlap detection
- ✅ **Historical Data Preservation**: Maintains snapshot of room/user data at booking time

### Advanced Features
- 🔒 **Immutability**: Booking data is immutable after creation
- 🛡️ **Exception Handling**: Comprehensive custom exception system
- 📊 **Data Integrity**: Defensive copying and validation throughout
- 🎨 **Rich Console Output**: Color-coded output for better readability
- 📝 **Audit Trail**: Print functions show data from newest to oldest

## 🏗️ Architecture

The system follows a clean, layered architecture:

```
┌─────────────────────────────────────┐
│         Main (Entry Point)          │
└─────────────────────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────┐
│       Service Layer (Service)       │
│  - Business Logic                   │
│  - Validation                       │
│  - Transaction Management           │
└─────────────────────────────────────┘
                 │
         ┌───────┴───────┐
         ▼               ▼
┌──────────────┐  ┌──────────────┐
│   Entities   │  │  Exceptions  │
│  - User      │  │  - Custom    │
│  - Room      │  │    Exception │
│  - Booking   │  │    Hierarchy │
│  - RoomType  │  │              │
└──────────────┘  └──────────────┘
```

## 📌 Technical Requirements

### Entity Specifications

#### User Entity
- Defined by **userId** and **balance**
- Balance can be updated
- Cannot have negative balance

#### Room Entity
- Defined by **roomNumber**, **type**, and **pricePerNight**
- Three room types: **Standard Suite**, **Junior Suite**, **Master Suite**
- Multiple rooms can share the same type with different prices

#### Booking Entity
- Custom designed with relations to User and Room
- Captures **snapshot** of room and user data at booking time
- Immutable after creation
- Stores check-in/check-out dates, total price, and number of nights

### Business Rules

1. **Booking Requirements**:
   - User must have sufficient balance
   - Room must be available for the requested period
   - Check-in date must be before check-out date
   - Balance is automatically deducted upon successful booking

2. **Data Consistency**:
   - `setRoom()` does **not** impact previously created bookings
   - `setRoom()` creates a room if it doesn't exist, updates if it does
   - `setUser()` creates a user if it doesn't exist, updates balance if it does

3. **Date Handling**:
   - Only year, month, and day are considered
   - Time components are normalized to zero

4. **Exception Handling**:
   - ValidationException for invalid inputs
   - UserNotFoundException when user doesn't exist
   - RoomNotFoundException when room doesn't exist
   - RoomNotAvailableException for booking conflicts
   - InsufficientBalanceException when balance is too low

## 🚀 Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven 3.6+

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/HamzaChehlaoui/Hotel-Reservation-System.git
   cd Hotel-Reservation-System
   ```

2. **Compile the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.hotel.Main"
   ```

## 💻 Usage

### Running the Test Case

The `Main.java` file includes a comprehensive test case that demonstrates all features:

```bash
mvn exec:java -Dexec.mainClass="com.hotel.Main"
```

### API Examples

#### Creating Rooms
```java
Service service = new Service();
service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
service.setRoom(3, RoomType.MASTER_SUITE, 3000);
```

#### Creating Users
```java
service.setUser(1, 5000);  // User 1 with balance 5000
service.setUser(2, 10000); // User 2 with balance 10000
```

#### Booking a Room
```java
Date checkIn = createDate(2026, 6, 30);
Date checkOut = createDate(2026, 7, 7);
service.bookRoom(1, 2, checkIn, checkOut); // User 1 books Room 2
```

#### Displaying Data
```java
service.printAll();       // Shows all rooms and bookings (newest first)
service.printAllUsers();  // Shows all users (newest first)
```

## 📁 Project Structure

```
Hotel-Reservation-System/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── hotel/
│                   ├── Main.java                          # Entry point & test case
│                   ├── entities/
│                   │   ├── Booking.java                   # Booking entity
│                   │   ├── Room.java                      # Room entity
│                   │   ├── User.java                      # User entity
│                   │   └── RoomType.java                  # Room type enum
│                   ├── service/
│                   │   └── Service.java                   # Business logic
│                   └── exception/
│                       ├── HotelReservationException.java # Base exception
│                       ├── InsufficientBalanceException.java
│                       ├── InvalidDateException.java
│                       ├── RoomNotAvailableException.java
│                       ├── RoomNotFoundException.java
│                       └── UserNotFoundException.java
├── pom.xml                                                # Maven configuration
└── README.md                                              # This file
```

## 🎨 Design Decisions

### 1. Snapshot Pattern in Booking

**Decision**: Store snapshot of room and user data at booking time

**Rationale**: 
- Ensures historical accuracy
- Allows `setRoom()` to modify rooms without affecting past bookings
- Provides audit trail for price changes

```java
// Booking captures room data at creation time
this.bookedRoomType = room.getRoomType();
this.bookedPricePerNight = room.getPricePerNight();
this.userBalanceAtBooking = user.getBalance();
```

### 2. Immutability in Booking

**Decision**: All booking fields are `final` and immutable

**Rationale**:
- Prevents accidental modification of historical records
- Ensures data integrity
- Thread-safe by design

### 3. Custom Exception Hierarchy

**Decision**: Create specific exceptions for each error case

**Rationale**:
- Better error handling and debugging
- Clear error messages for users
- Easier to test and maintain

### 4. Defensive Copying for Dates

**Decision**: Create copies of Date objects in getters/setters

**Rationale**:
- Prevents external modification of internal state
- Date is mutable, so defensive copying is essential

### 5. Service Layer Pattern

**Decision**: Centralize business logic in Service class

**Advantages**:
- Single entry point for all operations
- Easy to add new features
- Simplified testing

**Trade-offs**:
- Can become a "God Object" if not carefully managed
- See [Design Questions](#design-questions) for alternative approaches

## 🧪 Test Cases

The project includes comprehensive test cases in `Main.java`:

| Test | Description | Expected Result |
|------|-------------|-----------------|
| 1 | User 1 books Room 2 for 7 nights (14,000 cost) | ❌ Fails - Insufficient balance (has 5,000) |
| 2 | User 1 books with invalid dates (check-out before check-in) | ❌ Fails - InvalidDateException |
| 3 | User 1 books Room 1 for 1 night (1,000 cost) | ✅ Success - Balance: 5,000 → 4,000 |
| 4 | User 2 books Room 1 for 2 nights (2,000 cost) | ❌ Fails - Room not available (conflict with Test 3) |
| 5 | User 2 books Room 3 for 1 night (3,000 cost) | ✅ Success - Balance: 10,000 → 7,000 |
| 6 | Update Room 1 to Master Suite with price 10,000 | ✅ Success - Does not affect previous bookings |

### Expected Final State

**Users**:
- User 1: Balance = 4,000 (after booking Room 1)
- User 2: Balance = 7,000 (after booking Room 3)

**Rooms**:
- Room 1: Master Suite, 10,000/night (updated)
- Room 2: Junior Suite, 2,000/night
- Room 3: Master Suite, 3,000/night

**Successful Bookings**: 2 (Test 3 and Test 5)

## 📸 Screenshots

### Application Output

#### 1. System Startup
![System Loading](screenshots/Startup.png)

#### 2. Test Case Execution
![Test Case Results](screenshots/Test.png)

#### 3. printAll() Output
![PrintAll Output](screenshots/printAll.png)

#### 4. printAllUsers() Output
![PrintAllUsers Output](screenshots/printAllUsers.png)

> **Note**: Create a `screenshots/` directory and add your screenshots there.

## 🔧 Technologies

- **Language**: Java 8+
- **Build Tool**: Maven
- **Testing**: Manual testing via Main class
- **Architecture**: Layered Architecture (Service, Entity, Exception layers)

## 📝 Design Questions (Bonus)

### Question 1: Service-Based Architecture

**Q**: Is putting all functions in the same Service class recommended?

**A**: It depends on the project scale:

**Advantages** (Current Approach):
- ✅ Simple and straightforward for small projects
- ✅ Easy to understand and maintain
- ✅ Single point of coordination
- ✅ No need for dependency injection

**Trade-offs**:
- ⚠️ Can become a "God Object" in larger systems
- ⚠️ Harder to test individual components
- ⚠️ Low cohesion as system grows

**Recommended for Large Projects**:
- Separate into `UserService`, `RoomService`, `BookingService`
- Use Repository pattern for data access
- Apply Dependency Injection
- Implement SOLID principles more rigorously

### Question 2: Room Updates and Booking Impact

**Q**: We chose `setRoom()` to not impact previous bookings. What are alternatives?

**Current Approach** (Snapshot Pattern):
```java
// Booking stores room data at creation time
bookedRoomType = room.getRoomType();
bookedPricePerNight = room.getPricePerNight();
```

**Alternative Approaches**:

1. **Reference-Based** (Live Updates):
   - Bookings reference the actual Room object
   - Updates to rooms affect all bookings
   - ❌ Not recommended - breaks historical accuracy

2. **Versioning System**:
   - Create new version of Room on update
   - Bookings reference specific version
   - ✅ Good for audit trails but adds complexity

3. **Separate Price History Table**:
   - Track price changes over time
   - Query historical prices when needed
   - ✅ Good for enterprise systems with database

**My Recommendation**: **Snapshot Pattern** (Current Implementation)

**Justification**:
- ✅ Simplest to implement and understand
- ✅ Guarantees data consistency
- ✅ No database required
- ✅ Perfect for the given requirements
- ✅ Booking reflects accurate pricing at booking time
- ✅ Allows flexible room updates without side effects

For this project's scope, the snapshot pattern is ideal. For enterprise systems with databases, I'd recommend a Price History table with proper audit logging.

---

## 👨‍💻 Hamza Chehlaoui

**Technical Test Implementation**
- Project: Skypay Technical Test 2
- Purpose: Hotel Reservation System


<div align="center">
  <strong>Built with ❤️ using Java</strong>
</div>
