import java.util.Scanner;

/**
 * Entry point and simple menu loop for the Travel Management System.
 */
public class Main {
    public static void main(String[] args) {
        TravelSystem system = new TravelSystem();
        system.loadAll(); // Load data upon startup

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Professional Menu Display
            System.out.println("\n=============================================");
            System.out.println("     TRAVEL MANAGEMENT SYSTEM (CLI) - MODULE 1");
            System.out.println("=============================================");
            System.out.println("1. Add Travel Package");
            System.out.println("2. View All Packages");
            System.out.println("3. Register Customer");
            System.out.println("4. View All Customers");
            System.out.println("5. Create Booking");
            System.out.println("6. View All Bookings");
            System.out.println("7. Search Bookings by Customer Name");
            System.out.println("8. Save Data");
            System.out.println("9. Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Choose an option (1-9): ");

            String choice = sc.nextLine().trim();
            System.out.println("---------------------------------------------"); // Separator after choice

            try {
                switch (choice) {
                    case "1":
                        system.addPackageInteractive(sc);
                        break;
                    case "2":
                        system.viewPackages();
                        break;
                    case "3":
                        system.registerCustomerInteractive(sc);
                        break;
                    case "4":
                        system.viewCustomers();
                        break;
                    case "5":
                        system.createBookingInteractive(sc);
                        break;
                    case "6":
                        system.viewBookings();
                        break;
                    case "7":
                        System.out.print("Enter customer name to search: ");
                        String name = sc.nextLine().trim();
                        System.out.println("---------------------------------------------");
                        system.searchBookingsByCustomerName(name);
                        break;
                    case "8":
                        system.saveAll();
                        System.out.println("✅ Data saved successfully.");
                        break;
                    case "9":
                        system.saveAll();
                        System.out.println("👋 Exiting... Data saved. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please choose a number from 1 to 9.");
                }
                // Separator before the next menu reloads, unless we are exiting
                if (running) {
                    System.out.println("=============================================");
                }
            } catch (Exception e) {
                // General exception handler
                System.out.println("🛑 Critical Error: " + e.getMessage());
                System.out.println("=============================================");
            }
        }
        sc.close();
    }
}