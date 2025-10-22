import java.util.Scanner;

/**
 * Entry point and simple menu loop for the Travel Management System.
 * Includes user authentication logic with Login and Sign Up options.
 */
public class Main {
    public static void main(String[] args) {
        TravelSystem system = new TravelSystem();
        system.loadAll(); // Load data, including users

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        boolean loggedIn = false;

        // --- AUTHENTICATION MENU LOOP ---
        while (!loggedIn) {
            System.out.println("\n===== AUTHENTICATION MENU =====");
            System.out.println("1. Login (Existing User)");
            System.out.println("2. Sign Up (New User)");
            System.out.println("3. Exit Program");
            System.out.println("---------------------------------");
            System.out.print("Choose an option: ");
            
            String authChoice = sc.nextLine().trim();
            System.out.println("---------------------------------");

            switch (authChoice) {
                case "1": // LOGIN
                    System.out.print("Username: ");
                    String userL = sc.nextLine().trim();
                    System.out.print("Password: ");
                    String passL = sc.nextLine().trim();

                    if (system.authenticate(userL, passL)) {
                        loggedIn = true;
                        System.out.println(" Login successful! Welcome to the system.");
                    } else {
                        System.out.println(" Invalid credentials. Try again.");
                    }
                    break;

                case "2": // SIGN UP
                    System.out.println("--- NEW USER REGISTRATION ---");
                    System.out.print("Enter new Username: ");
                    String userS = sc.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String passS = sc.nextLine().trim();

                    if (userS.isEmpty() || passS.isEmpty()) {
                        System.out.println(" Username and Password cannot be empty.");
                        break;
                    }

                    if (system.registerUser(userS, passS)) {
                        System.out.println(" Registration successful! Please log in now using your new credentials.");
                    } else {
                        System.out.println(" Username already taken. Try a different one.");
                    }
                    break;
                    
                case "3": // EXIT PROGRAM
                    System.out.println("? Exiting Program. Goodbye!");
                    sc.close();
                    return; 

                default:
                    System.out.println(" Invalid option. Choose 1, 2, or 3.");
            }
        }
        
        // --- MAIN MENU LOOP (Runs only after successful login) ---
        while (running) {
            
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
            System.out.println("---------------------------------------------");

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
                        System.out.println(" Data saved successfully.");
                        break;
                    case "9":
                        system.saveAll();
                        System.out.println(" Exiting... Data saved. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println(" Invalid option. Please choose a number from 1 to 9.");
                }

                if (running) {
                    System.out.println("=============================================");
                }
            } catch (Exception e) {
                System.out.println(" Critical Error: " + e.getMessage());
                System.out.println("=============================================");
            }
        }
        sc.close();
    }
}