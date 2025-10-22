import java.io.*;
import java.util.*;

/**
 * Core system: holds lists, interactive methods, and file handling.
 */
public class TravelSystem {
    private List<TravelPackage> packages = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<User> users = new ArrayList<>(); 

    // filenames (using final for constants)
    private final String PACK_FILE = "packages.txt";
    private final String CUST_FILE = "customers.txt";
    private final String BOOK_FILE = "bookings.txt";
    private final String USER_FILE = "users.txt"; 

    // counters for ids
    private int pkgCounter = 1;
    private int custCounter = 1;
    private int bookCounter = 1;

    /* ------------------ Authentication & Registration ------------------ */

    public boolean authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    // User registration method
    public boolean registerUser(String username, String password) {
        // Check if user already exists
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // User already exists
            }
        }
        // Add new user
        users.add(new User(username, password));
        // Save data immediately after registration to persist the new user
        saveAll(); 
        return true; // Registration successful
    }

    /* ------------------ Interactive (menu) helpers ------------------ */

    public void addPackageInteractive(Scanner sc) {
        System.out.println("--- Add New Travel Package ---");
        try {
            System.out.print("Destination: ");
            String dest = sc.nextLine().trim();
            System.out.print("Duration in days (number): ");
            int dur = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine().trim());
            
            String id = String.format("P%03d", pkgCounter++);
            TravelPackage tp = new TravelPackage(id, dest, dur, price);
            packages.add(tp);
            System.out.println(" Package " + id + " added successfully: " + dest + ".");
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number entry for duration or price. Package not added.");
        } catch (Exception e) {
            System.out.println(" An error occurred. Package not added.");
        }
    }

    public void viewPackages() {
        System.out.println("--- Available Packages (" + packages.size() + ") ---");
        if (packages.isEmpty()) {
            System.out.println("No packages available.");
        } else {
            for (TravelPackage p : packages) System.out.println(p.getDisplay());
        }
    }

    public void registerCustomerInteractive(Scanner sc) {
        System.out.println("--- Register New Customer ---");
        try {
            System.out.print("Customer name: ");
            String name = sc.nextLine().trim();
            System.out.print("Phone: ");
            String phone = sc.nextLine().trim();
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            
            String id = String.format("C%03d", custCounter++);
            Customer c = new Customer(id, name, phone, email);
            customers.add(c);
            System.out.println(" Customer " + id + " registered successfully: " + name + ".");
        } catch (Exception e) {
             System.out.println(" An error occurred during customer registration.");
        }
    }

    public void viewCustomers() {
        System.out.println("--- Registered Customers (" + customers.size() + ") ---");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer c : customers) System.out.println(c.getDetails());
        }
    }

    public void createBookingInteractive(Scanner sc) {
        System.out.println("--- Create New Booking ---");
        if (customers.isEmpty() || packages.isEmpty()) {
            System.out.println(" Cannot create booking. Need at least one customer and one package.");
            return;
        }

        System.out.println("--- Available Customers (ID | Name):");
        for (Customer c : customers) System.out.println("  " + c.getId() + " | " + c.getName());
        System.out.print("Enter Customer ID: ");
        String cid = sc.nextLine().trim();
        Customer selectedCustomer = findCustomerById(cid);
        if (selectedCustomer == null) { System.out.println(" Customer not found for ID: " + cid); return; }

        System.out.println("\n--- Available Packages (ID | Destination | Price):");
        for (TravelPackage p : packages) System.out.println("  " + p.getId() + " | " + p.getDestination() + " | " + String.format("%.2f", p.getPrice()));
        System.out.print("Enter Package ID: ");
        String pid = sc.nextLine().trim();
        TravelPackage selectedPackage = findPackageById(pid);
        if (selectedPackage == null) { System.out.println(" Package not found for ID: " + pid); return; }

        System.out.print("Booking date (YYYY-MM-DD): ");
        String date = sc.nextLine().trim();

        String id = String.format("B%03d", bookCounter++);
        Booking b = new Booking(id, cid, pid, date);
        bookings.add(b);
        System.out.println(" Booking " + id + " created for " + selectedCustomer.getName() + " on package to " + selectedPackage.getDestination() + ".");
    }

    public void viewBookings() {
        System.out.println("--- All Bookings (" + bookings.size() + ") ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            for (Booking b : bookings) System.out.println(b.getDisplay(customers, packages));
        }
    }

    public void searchBookingsByCustomerName(String namePart) {
        System.out.println("--- Search Results for '" + namePart + "' ---");
        boolean found = false;
        for (Booking b : bookings) {
            Customer c = findCustomerById(b.getCustomerId());
            if (c != null && c.getName().toLowerCase().contains(namePart.toLowerCase())) {
                System.out.println(b.getDisplay(customers, packages));
                found = true;
            }
        }
        if (!found) System.out.println("No bookings found for that name.");
    }

    /* ------------------ Helpers ------------------ */

    private Customer findCustomerById(String id) {
        for (Customer c : customers) if (c.getId().equals(id)) return c;
        return null;
    }

    private TravelPackage findPackageById(String id) {
        for (TravelPackage p : packages) if (p.getId().equals(id)) return p;
        return null;
    }

    /* ------------------ Persistence: save and load ------------------ */

    public void saveAll() {
        saveListToFile(PACK_FILE, packagesToSaveBlocks());
        saveListToFile(CUST_FILE, customersToSaveBlocks());
        saveListToFile(BOOK_FILE, bookingsToSaveBlocks());
        saveListToFile(USER_FILE, usersToSaveBlocks()); 
    }

    private List<String> packagesToSaveBlocks() {
        List<String> all = new ArrayList<>();
        for (TravelPackage p : packages) {
            all.add(p.toSaveString());
        }
        return all;
    }

    private List<String> customersToSaveBlocks() {
        List<String> all = new ArrayList<>();
        for (Customer c : customers) {
            all.add(c.toSaveString());
        }
        return all;
    }

    private List<String> bookingsToSaveBlocks() {
        List<String> all = new ArrayList<>();
        for (Booking b : bookings) {
            all.add(b.toSaveString());
        }
        return all;
    }
    
    private List<String> usersToSaveBlocks() {
        List<String> all = new ArrayList<>();
        for (User u : users) {
            all.add(u.toSaveString());
        }
        return all;
    }


    private void saveListToFile(String filename, List<String> blocks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String block : blocks) {
                pw.println(block);
            }
        } catch (IOException e) {
            System.out.println("Failed to save " + filename + ": " + e.getMessage());
        }
    }

    public void loadAll() {
        // load packages
        List<List<String>> pblocks = readBlocksFromFile(PACK_FILE);
        packages.clear();
        for (List<String> b : pblocks) {
            TravelPackage tp = TravelPackage.fromBlock(b);
            if (tp != null) packages.add(tp);
        }

        // load customers
        List<List<String>> cblocks = readBlocksFromFile(CUST_FILE);
        customers.clear();
        for (List<String> b : cblocks) {
            Customer c = Customer.fromBlock(b);
            if (c != null) customers.add(c);
        }

        // load bookings
        List<List<String>> bblocks = readBlocksFromFile(BOOK_FILE);
        bookings.clear();
        for (List<String> b : bblocks) {
            Booking bk = Booking.fromBlock(b);
            if (bk != null) bookings.add(bk);
        }

        // load users
        List<List<String>> ublocks = readBlocksFromFile(USER_FILE);
        users.clear();
        for (List<String> b : ublocks) {
            User u = User.fromBlock(b);
            if (u != null) users.add(u);
        }
        
        // --- REMOVED DEFAULT USER CREATION ---
        // Ab agar users.txt file khali ya maujood nahi hai, toh user ko Sign Up karna padega.


        // update counters so new IDs don't clash
        updateCounters();
        System.out.println("Data loaded: " + packages.size() + " packages, " +
                customers.size() + " customers, " + bookings.size() + " bookings, " +
                users.size() + " users.");
    }

    // Reads file and splits into blocks separated by the line with ----
    private List<List<String>> readBlocksFromFile(String filename) {
        List<List<String>> blocks = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) return blocks;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            List<String> current = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("------------------------")) {
                    if (!current.isEmpty()) {
                        blocks.add(new ArrayList<>(current));
                        current.clear();
                    }
                } else {
                    if (!line.trim().isEmpty()) current.add(line);
                }
            }
            // last block (in case no trailing dash)
            if (!current.isEmpty()) blocks.add(new ArrayList<>(current));
        } catch (IOException e) {
            System.out.println("Failed to read " + filename + ": " + e.getMessage());
        }

        return blocks;
    }

    // ensure counters reflect loaded items
    private void updateCounters() {
        int maxP = 0, maxC = 0, maxB = 0;
        for (TravelPackage p : packages) {
            String id = p.getId();
            try { maxP = Math.max(maxP, Integer.parseInt(id.replaceAll("[^0-9]", ""))); } catch (Exception ignored) {}
        }
        for (Customer c : customers) {
            String id = c.getId();
            try { maxC = Math.max(maxC, Integer.parseInt(id.replaceAll("[^0-9]", ""))); } catch (Exception ignored) {}
        }
        for (Booking b : bookings) {
            String id = b.getId();
            try { maxB = Math.max(maxB, Integer.parseInt(id.replaceAll("[^0-9]", ""))); } catch (Exception ignored) {}
        }
        pkgCounter = maxP + 1;
        custCounter = maxC + 1;
        bookCounter = maxB + 1;
    }
}