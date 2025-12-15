package com.hotel;

import com.hotel.entities.RoomType;
import com.hotel.exception.HotelReservationException;
import com.hotel.service.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Test Case from Technical Requirements PDF
 */
public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();

        showLoadingAnimation();

        System.out.println(CYAN + BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + BOLD + "║         HOTEL RESERVATION SYSTEM - TEST CASE                 ║" + RESET);
        System.out.println(
                CYAN + BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET + "\n");

        System.out.println(YELLOW + BOLD + "▶ Creating 3 rooms..." + RESET);
        service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
        service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
        service.setRoom(3, RoomType.MASTER_SUITE, 3000);
        System.out.println(GREEN + "✓ Room 1: STANDARD, 1000/night" + RESET);
        System.out.println(GREEN + "✓ Room 2: JUNIOR, 2000/night" + RESET);
        System.out.println(GREEN + "✓ Room 3: MASTER, 3000/night" + RESET + "\n");

        System.out.println(YELLOW + BOLD + "▶ Creating 2 users..." + RESET);
        service.setUser(1, 5000);
        service.setUser(2, 10000);
        System.out.println(GREEN + "✓ User 1: Balance 5000" + RESET);
        System.out.println(GREEN + "✓ User 2: Balance 10000" + RESET + "\n");

        System.out.println(PURPLE + BOLD + "═══════════════════════════════════════════════════════════════" + RESET);
        System.out.println(PURPLE + BOLD + "                    BOOKING ATTEMPTS" + RESET);
        System.out.println(
                PURPLE + BOLD + "═══════════════════════════════════════════════════════════════" + RESET + "\n");

        System.out.println(
                BLUE + BOLD + "▶ Test 1: " + RESET + "User 1 books Room 2 (30/06 - 07/07) - 7 nights × 2000 = 14000");
        try {
            service.bookRoom(1, 2, createDate(2026, 6, 30), createDate(2026, 7, 7));
            System.out.println(GREEN + "✓ SUCCESS" + RESET + "\n");
        } catch (HotelReservationException e) {
            System.out.println(RED + "✗ FAILED: " + e.getMessage() + RESET + "\n");
        }

        System.out.println(BLUE + BOLD + "▶ Test 2: " + RESET + "User 1 books Room 2 (07/07 - 30/06) - Invalid dates");
        try {
            service.bookRoom(1, 2, createDate(2026, 7, 7), createDate(2026, 6, 30));
            System.out.println(GREEN + "✓ SUCCESS" + RESET + "\n");
        } catch (HotelReservationException e) {
            System.out.println(RED + "✗ FAILED: " + e.getMessage() + RESET + "\n");
        }

        System.out.println(
                BLUE + BOLD + "▶ Test 3: " + RESET + "User 1 books Room 1 (07/07 - 08/07) - 1 night × 1000 = 1000");
        try {
            service.bookRoom(1, 1, createDate(2026, 7, 7), createDate(2026, 7, 8));
            System.out.println(GREEN + "✓ SUCCESS" + RESET + "\n");
        } catch (HotelReservationException e) {
            System.out.println(RED + "✗ FAILED: " + e.getMessage() + RESET + "\n");
        }

        System.out.println(
                BLUE + BOLD + "▶ Test 4: " + RESET + "User 2 books Room 1 (07/07 - 09/07) - 2 nights × 1000 = 2000");
        try {
            service.bookRoom(2, 1, createDate(2026, 7, 7), createDate(2026, 7, 9));
            System.out.println(GREEN + "✓ SUCCESS" + RESET + "\n");
        } catch (HotelReservationException e) {
            System.out.println(RED + "✗ FAILED: " + e.getMessage() + RESET + "\n");
        }

        System.out.println(
                BLUE + BOLD + "▶ Test 5: " + RESET + "User 2 books Room 3 (07/07 - 08/07) - 1 night × 3000 = 3000");
        try {
            service.bookRoom(2, 3, createDate(2026, 7, 7), createDate(2026, 7, 8));
            System.out.println(GREEN + "✓ SUCCESS" + RESET + "\n");
        } catch (HotelReservationException e) {
            System.out.println(RED + "✗ FAILED: " + e.getMessage() + RESET + "\n");
        }

        System.out.println(YELLOW + BOLD + "═══════════════════════════════════════════════════════════════" + RESET);
        System.out.println(YELLOW + BOLD + "▶ setRoom(1, MASTER_SUITE, 10000) - Updating Room 1" + RESET);
        System.out.println(YELLOW + "  (Should NOT impact previous bookings)" + RESET);
        System.out.println(
                YELLOW + BOLD + "═══════════════════════════════════════════════════════════════" + RESET + "\n");
        service.setRoom(1, RoomType.MASTER_SUITE, 10000);

        System.out.println(CYAN + BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + BOLD + "║                      printAll() OUTPUT                       ║" + RESET);
        System.out.println(
                CYAN + BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET + "\n");
        service.printAll();

        System.out.println(
                "\n" + GREEN + BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(GREEN + BOLD + "║                   printAllUsers() OUTPUT                     ║" + RESET);
        System.out.println(
                GREEN + BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET + "\n");
        service.printAllUsers();
    }

    private static void showLoadingAnimation() throws InterruptedException {
        System.out.print(CYAN + BOLD + "Loading System");
        for (int i = 0; i < 5; i++) {
            Thread.sleep(200);
            System.out.print(".");
        }
        System.out.println(RESET + "\nSuccess!\n");
    }

    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
