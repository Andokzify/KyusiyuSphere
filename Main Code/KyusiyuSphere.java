package kyusiyusphere;
import java.util.ArrayList;
import java.util.Scanner;


class UserAccount {
    String name;
    String email;
    int age;
    String password;
    int role; // 1-user, 2-owner, 3-admin

    ArrayList<String> myReservations = new ArrayList<>();

    UserAccount(String name, String email, int age, String password, int role) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.role = role;
    }
}

class Place {
    String name;
    String description;
    String location;
    UserAccount owner;
    String label;
    boolean reservationAvailable;
    boolean walkInAvailable;
    String contact;
    String price;

    ArrayList<String> reservations = new ArrayList<>();

    public Place(String name, String description, String location,
                 UserAccount owner, String label,
                 boolean reservationAvailable, boolean walkInAvailable,
                 String contact, String price) {

        this.name = name;
        this.description = description;
        this.location = location;
        this.owner = owner;
        this.label = label;
        this.reservationAvailable = reservationAvailable;
        this.walkInAvailable = walkInAvailable;
        this.contact = contact;
        this.price = price;
    }

    public String toString() {
        return "\n----------------------" +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nLocation: " + location +
                "\nOwner: " + owner.name +
                "\nLabel: " + label +
                "\nReservation: " + (reservationAvailable ? "Yes" : "No") +
                "\nWalk-in: " + (walkInAvailable ? "Yes" : "No") +
                "\nContact: " + contact +
                "\nPrice: " + price +
                "\n----------------------";
    }
}

/* ================= MAIN ================= */

    public class KyusiyuSphere {
        
    static ArrayList<UserAccount> users = new ArrayList<>();
    static ArrayList<Place> places = new ArrayList<>();
    static ArrayList<String> suggestions = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static int userLoginCount = 0;
    static int ownerLoginCount = 0;
    static int adminLoginCount = 0;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n=== KyusiSphere ===");
            System.out.println("1 - Register");
            System.out.println("2 - Login");
            System.out.println("3 - Exit");

            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.out.println("Goodbye!");
            }

        } while (choice != 3);
    }

    /* ================= PASSWORD VALIDATION ================= */

    static boolean isValidPassword(String password) {

        if (password.length() < 8) {
            return false;
        }

        int letterCount = 0;
        int numberCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isLetter(ch)) {
                letterCount++;
            } 
            else if (Character.isDigit(ch)) {
                numberCount++;
            }
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
            System.out.print("Password (min 8 chars, at least 2 letters & 2 numbers): ");
            password = scanner.nextLine();

            if (!isValidPassword(password)) {
                System.out.println("Invalid password format!");
                System.out.println("Must be at least 8 characters with 2 letters and 2 numbers.");
            }

        } while (!isValidPassword(password));

        System.out.print("Role (1-user, 2-owner, 3-admin): ");
        int role = scanner.nextInt();
        scanner.nextLine();

        users.add(new UserAccount(name, email, age, password, role));
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
            if (u.name.equals(name) && u.password.equals(pass)) {

                if (u.role == 1) userLoginCount++;
                if (u.role == 2) ownerLoginCount++;
                if (u.role == 3) adminLoginCount++;

                if (u.role == 1) userMenu(u);
                else if (u.role == 2) ownerMenu(u);
                else if (u.role == 3) adminMenu(u);

                return;
            }
        }

        System.out.println("Login failed.");
    }

    /* ================= USER MENU ================= */

    static void userMenu(UserAccount user) {
        int choice;

        do {
            System.out.println("\nUser: " + user.name);
            System.out.println("1 - View places and reservation");
            System.out.println("2 - Search by label");
            System.out.println("3 - Suggestion to devoloper of KYUSISPEHERE");
            System.out.println("4 - View My Reservations");
            System.out.println("5 - Logout");

            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewPlacesAndReserve(user);
                case 2 -> searchByLabel();
                case 3 -> {
                    System.out.print("Suggestion: ");
                    suggestions.add(user.name + ": " + scanner.nextLine());
                    System.out.println("Thank you for your suggestion!");
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

            System.out.println("\n" + (i + 1) + ". " + p.name);
            System.out.println("   Description: " + p.description);
            System.out.println("   Location: " + p.location);
            System.out.println("   Label: " + p.label);
            System.out.println("   Price: " + p.price);
            System.out.println("   Reservation Available: " + (p.reservationAvailable ? "Yes" : "No"));
             System.out.println("   Walk in Available: " + (p.walkInAvailable ? "Yes" : "No"));
        }

        System.out.print("\nReserve which? (0 cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice <= 0 || choice > places.size())
            return;

        Place selected = places.get(choice - 1);

        if (!selected.reservationAvailable) {
            System.out.println("Reservations not allowed.");
            return;
        }

        System.out.print("Enter reservation date: ");
        String date = scanner.nextLine();

        System.out.print("Enter reservation time: ");
        String time = scanner.nextLine();

        String reservationDetail = "Place: " + selected.name +
                                   " | Date: " + date +
                                   " | Time: " + time;

        selected.reservations.add(reservationDetail);
        user.myReservations.add(reservationDetail);

        System.out.println("Reserved successfully!");
    }

    static void viewMyReservations(UserAccount user) {
        System.out.println("\nMy Reservations:");

        if (user.myReservations.isEmpty()) {
            System.out.println("No reservations.");
        } else {
            for (String r : user.myReservations)
                System.out.println("- " + r);
        }
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

        if (!found)
            System.out.println("No places found.");
    }

    /* ================= OWNER MENU ================= */

    static void ownerMenu(UserAccount owner) {
        int choice;

        do {
            System.out.println("\nOwner: " + owner.name);
            System.out.println("1 - Dashboard");
            System.out.println("2 - View My Places");
            System.out.println("3 - Add Place");
            System.out.println("4 - Edit My Place");
            System.out.println("5 - Delete My Place");
            System.out.println("6 - Logout");

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
            if (p.owner == owner) {
                System.out.println(p);

                if (p.reservations.isEmpty())
                    System.out.println("No reservations.");
                else {
                    System.out.println("Reservations:");
                    for (String r : p.reservations)
                        System.out.println("- " + r);
                }

                found = true;
            }
        }

        if (!found)
            System.out.println("No places yet.");
    }

    static void viewMyPlaces(UserAccount owner) {

        System.out.println("YOUR PLACES: ");

        boolean found = false;

        for (Place p : places) {
            if (p.owner == owner) {
                System.out.println(p);
                found = true;
            }
        }

        if (!found)
            System.out.println("No places yet.");
    }

    static void addPlace(UserAccount owner) {

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.print("Label: ");
        String label = scanner.nextLine();

        System.out.print("Reservation (true/false): ");
        boolean reservation = scanner.nextBoolean();

        System.out.print("Walk-in (true/false): ");
        boolean walk = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Contact: ");
        String contact = scanner.nextLine();

        System.out.print("Price: ");
        String price = scanner.nextLine();

        places.add(new Place(name, description, location, owner, label, reservation, walk, contact, price));

        System.out.println("Place added.");
    }

    static void editMyPlace(UserAccount owner) {

        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).owner == owner) {
                System.out.println(i + " - " + places.get(i).name);
            }
        }

        System.out.print("Edit which? ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < places.size()) {
            Place p = places.get(index);

            if (p.owner != owner) {
                System.out.println("Not your place.");
                return;
            }

            System.out.print("New description: ");
            p.description = scanner.nextLine();

            System.out.print("New price: ");
            p.price = scanner.nextLine();

           System.out.print("Reservation available (true/false): ");
           p.reservationAvailable = scanner.nextBoolean();

          System.out.print("Walk-in available (true/false): ");
        p.walkInAvailable = scanner.nextBoolean();
        scanner.nextLine();


            System.out.println("Updated.");
        }
    }

    static void deleteMyPlace(UserAccount owner) {

        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).owner == owner) {
                System.out.println(i + " - " + places.get(i).name);
            }
        }

        System.out.print("Delete which? ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < places.size()) {

            if (places.get(index).owner == owner) {
                places.remove(index);
                System.out.println("Deleted.");
            } else {
                System.out.println("Not your place.");
            }
        }
    }

    /* ================= ADMIN MENU ================= */

    static void adminMenu(UserAccount admin) {

        int choice;

        do {
            System.out.println("\nAdmin: " + admin.name);
            System.out.println("1 - View all places");
              System.out.println("2 - Delete Places");
            System.out.println("3 - View suggestions");          
            System.out.println("4 - View login stats");
            System.out.println("5 - Logout");

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
        for (Place p : places)
            System.out.println(p);
    }

    
    static void deletePlace() {
        viewAllPlaces();

        System.out.print("Enter number to delete (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= places.size()) {
            places.remove(choice - 1);
            System.out.println("Place deleted.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void viewSuggestions() {
        if (suggestions.isEmpty())
            System.out.println("No suggestions.");
        else
            for (String s : suggestions)
                System.out.println("- " + s);
    }

    static void viewLoginStats() {
        System.out.println("User logins: " + userLoginCount);
        System.out.println("Owner logins: " + ownerLoginCount);
        System.out.println("Admin logins: " + adminLoginCount);
    }

// Simple method so GUI can call registration
public static void registerFromGUI(String name, String email, int age, String pass, int role, String contact, String extra) {
    if (!isValidPassword(pass)) {
        System.out.println("Invalid password");
        return;
    }
    users.add(new UserAccount(name, email, age, pass, role));
    System.out.println("Registered via GUI: " + name + " (Role: " + role + ")");
}
        
    
    }
    
