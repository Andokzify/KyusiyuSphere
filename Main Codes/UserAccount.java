package kyusiyusphere;
import java.util.ArrayList;

public class UserAccount {
    public int id; // Added ID field
    public String name;
    public String email;
    public int age;
    public String password;
    public int role;
    public String contact; 
    public ArrayList<String> myReservations = new ArrayList<>();

    // Updated master constructor to include ID
    public UserAccount(int id, String name, String email, int age, String password, int role, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.role = role;
        this.contact = contact != null ? contact : "N/A";
    }

    // Legacy constructor for offline/GUI registration logic before DB insertion
    public UserAccount(String name, String email, int age, String password, int role) {
        this(0, name, email, age, password, role, "N/A");
    }
 
    public String getName() { return this.name; }
    public String getPassword() { return this.password; }
    public int getRole() { return this.role; }
    public ArrayList<String> getReservations() { return this.myReservations; }
    public void addReservation(String detail) { this.myReservations.add(detail); }
}