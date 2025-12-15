# Skypay Technical Test 2 - Design Questions & Answers

## 1. Service Design Approach
**Question:** Suppose we put all the functions inside the same service. Is this the recommended approach? Please explain.

**Answer:**
No, putting all functions in a single `Service` class is generally **not recommended** for larger or scalable applications, although it may be acceptable for this specific simplified technical test.

**Reasoning:**
*   **Single Responsibility Principle (SRP):** A single class handling Users, Rooms, and Bookings violates SRP. It does too many things.
*   **Separation of Concerns:** ideally, we should have:
    *   `UserService`: Management of users and balances.
    *   `RoomService`: Management of rooms definitions.
    *   `BookingService`: Orchestration of bookings, dependent on the other two.
*   **Maintainability:** As the system grows, a monolithic Service class becomes a "God Class" that is hard to test and maintain.

**Conclusion:** While I implemented a single `Service` class to strictly follow the specific requirements of this test, in a real-world production environment, I would split these responsibilities into dedicated services.

---

## 2. Handling Room Updates
**Question:** In this design, we chose to have a function `setRoom(...)` that should not impact the previous bookings. What is another way? What is your recommendation? Please explain and justify.

**Answer:**

**Current Implementation (Snapshot Approach):**
In my solution, I handled this by storing a **snapshot** of the room details (price, type) inside the `Booking` entity at the time of creation.
*   **Pros:** Simple, robust, and ensures historical accuracy. The booking record is immutable regarding the price agreed upon.
*   **Cons:** Data duplication.

**Alternative Approach: Data Versioning (Temporal Patterns)**
Instead of overwriting the `Room` object when `setRoom` is called, we could version the room data.
*   **How:** The `Room` entity would have a validity period (`validFrom`, `validTo`). A "update" would actually create a new row/object for the room with the new price and a new validity start date, while "closing" the previous record. Bookings would link to the specific version of the room that was active at that time.

**Recommendation:**
For a Hotel Reservation System specifically, **Snapshotting** (the approach I implemented) is often preferred for the specific use case of *Billing*.
**Justification:** When a user books a room, they agree to a specific contract. Even if the room's base configuration changes later (e.g., versioning), the *Booking* itself should retain the exact price and conditions of that specific contract. Therefore, embedding the critical billing data (price) into the Booking entity is the safest and most practical way to ensure financial consistency without complex temporal joins.

