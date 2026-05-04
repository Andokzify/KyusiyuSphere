package kyusiyusphere;

import java.util.ArrayList;
import java.util.Scanner;

public class KyusiyuSphere {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<UserAccount> users = new ArrayList<>();
    static ArrayList<Place> places = new ArrayList<>();
    static ArrayList<String> suggestions = new ArrayList<>();

    // ADDED: The missing tracking variables to fix the compilation error!
    static int userLoginCount = 0;
    static int ownerLoginCount = 0;
    static int adminLoginCount = 0;

    public static void main(String[] args) {
        System.out.println("=== Starting KyusiyuSphere GUI ===");
        // Ensure DatabaseHelper.java exists and has this method
        DatabaseHelper.initializeDatabase(); 
        // Ensure startingScreen.java is a JFrame
        new startingScreen().setVisible(true);
    }

    /* ================= PASSWORD VALIDATION ================= */
    static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        int letterCount = 0;
        int numberCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLetter(ch)) letterCount++;
            else if (Character.isDigit(ch)) numberCount++;
        }
        return letterCount >= 2 && numberCount >= 2;
    }

    /* ================= REGISTER ================= */
    static void register() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        String password;
        do {
            System.out.print("Password (min 8 chars, 2 letters & 2 numbers): ");
            password = scanner.nextLine();
            if (!isValidPassword(password)) System.out.println("Invalid format!");
        } while (!isValidPassword(password));

        System.out.print("Role (1-user, 2-owner, 3-admin): ");
        int role = scanner.nextInt();
        scanner.nextLine();

        users.add(new UserAccount(0, name, email, age, password, role, "N/A"));
        System.out.println("Registered successfully!");
    }

    /* ================= LOGIN ================= */
    static void login() {
        if (users.isEmpty()) {
            System.out.println("No users yet.");
            return;
        }
        System.out.print("Username: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        for (UserAccount u : users) {
            // These getter methods and login counts will now compile perfectly
            if (u.getName().equals(name) && u.getPassword().equals(pass)) {
                if (u.getRole() == 1) { userLoginCount++; userMenu(u); }
                else if (u.getRole() == 2) { ownerLoginCount++; ownerMenu(u); }
                else if (u.getRole() == 3) { adminLoginCount++; adminMenu(u); }
                return;
            }
        }
        System.out.println("Login failed.");
    }

    /* ================= USER MENU ================= */
    static void userMenu(UserAccount user) {
        int choice;
        do {
            System.out.println("\nUser: " + user.getName());
            System.out.println("1 - View places and reserve\n2 - Search by label\n3 - Suggestion\n4 - View My Reservations\n5 - Logout");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewPlacesAndReserve(user);
                case 2 -> searchByLabel();
                case 3 -> {
                    System.out.print("Suggestion: ");
                    suggestions.add(user.getName() + ": " + scanner.nextLine());
                }
                case 4 -> viewMyReservations(user);
            }
        } while (choice != 5);
    }

    static void viewPlacesAndReserve(UserAccount user) {
        if (places.isEmpty()) {
            System.out.println("No places available.");
            return;
        }
        for (int i = 0; i < places.size(); i++) {
            Place p = places.get(i);
            System.out.println((i + 1) + ". " + p.name + " | " + p.price);
        }
        System.out.print("\nReserve which? (0 cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice <= 0 || choice > places.size()) return;

        Place selected = places.get(choice - 1);
        if (!selected.reservationAvailable) {
            System.out.println("Not allowed.");
            return;
        }
        System.out.print("Date: ");
        String date = scanner.nextLine();
        System.out.print("Time: ");
        String time = scanner.nextLine();

        String detail = "Place: " + selected.name + " | Date: " + date + " | Time: " + time;
        selected.reservations.add(detail);
        user.addReservation(detail);
        System.out.println("Reserved!");
    }

    static void viewMyReservations(UserAccount user) {
        if (user.getReservations().isEmpty()) System.out.println("No reservations.");
        else user.getReservations().forEach(r -> System.out.println("- " + r));
    }

    static void searchByLabel() {
        System.out.print("Enter label: ");
        String label = scanner.nextLine();
        boolean found = false;
        for (Place p : places) {
            if (p.label.equalsIgnoreCase(label)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No matches.");
    }

    /* ================= OWNER MENU ================= */
    static void ownerMenu(UserAccount owner) {
        int choice;
        do {
            System.out.println("\nOwner: " + owner.getName());
            System.out.println("1 - Dashboard\n2 - View My Places\n3 - Add Place\n4 - Edit\n5 - Delete\n6 - Logout");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> ownerDashboard(owner);
                case 2 -> viewMyPlaces(owner);
                case 3 -> addPlace(owner);
                case 4 -> editMyPlace(owner);
                case 5 -> deleteMyPlace(owner);
            }
        } while (choice != 6);
    }

    static void ownerDashboard(UserAccount owner) {
        boolean found = false;
        for (Place p : places) {
            if (p.owner.equals(owner)) {
                System.out.println(p);
                p.reservations.forEach(r -> System.out.println("  - " + r));
                found = true;
            }
        }
        if (!found) System.out.println("No places yet.");
    }

    static void viewMyPlaces(UserAccount owner) {
        places.stream().filter(p -> p.owner.equals(owner)).forEach(System.out::println);
    }

    static void addPlace(UserAccount owner) {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Description: "); String desc = scanner.nextLine();
        System.out.print("Location: "); String loc = scanner.nextLine();
        System.out.print("Label: "); String lab = scanner.nextLine();
        System.out.print("Reservation (true/false): "); boolean res = scanner.nextBoolean();
        System.out.print("Walk-in (true/false): "); boolean walk = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Contact: "); String con = scanner.nextLine();
        System.out.print("Price: "); String price = scanner.nextLine();

        places.add(new Place(0, name, desc, loc, owner, lab, res, walk, con, price));
        System.out.println("Added.");
    }

    static void editMyPlace(UserAccount owner) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).owner.equals(owner)) System.out.println(i + " - " + places.get(i).name);
        }
        System.out.print("Edit index: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < places.size() && places.get(index).owner.equals(owner)) {
            Place p = places.get(index);
            System.out.print("New description: "); p.description = scanner.nextLine();
            System.out.print("New price: "); p.price = scanner.nextLine();
            System.out.println("Updated.");
        }
    }

    static void deleteMyPlace(UserAccount owner) {
        System.out.print("Delete index: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < places.size() && places.get(index).owner.equals(owner)) {
            places.remove(index);
            System.out.println("Deleted.");
        }
    }

    /* ================= ADMIN MENU ================= */
    static void adminMenu(UserAccount admin) {
        int choice;
        do {
            System.out.println("\nAdmin: " + admin.getName());
            System.out.println("1 - All Places\n2 - Delete\n3 - Suggestions\n4 - Stats\n5 - Logout");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> viewAllPlaces();
                case 2 -> deletePlace();
                case 3 -> viewSuggestions();
                case 4 -> viewLoginStats();
            }
        } while (choice != 5);
    }

    static void viewAllPlaces() {
        places.forEach(System.out::println);
    }

    static void deletePlace() {
        System.out.print("Enter index to delete: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice > 0 && choice <= places.size()) {
            places.remove(choice - 1);
        }
    }

    static void viewSuggestions() {
        if (suggestions.isEmpty()) System.out.println("None.");
        else suggestions.forEach(s -> System.out.println("- " + s));
    }

    static void viewLoginStats() {
        System.out.println("User logins: " + userLoginCount);
        System.out.println("Owner logins: " + ownerLoginCount);
        System.out.println("Admin logins: " + adminLoginCount);
    }

    public static void registerFromGUI(String name, String email, int age, String pass, int role, String contact, String extra) {
        if (!isValidPassword(pass)) return;
        users.add(new UserAccount(0, name, email, age, pass, role, contact));
    }
}