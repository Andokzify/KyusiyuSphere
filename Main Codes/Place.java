package kyusiyusphere;
import java.util.ArrayList;

public class Place {
    public int id; // Added missing ID field
    public String name;
    public String description;
    public String location;    
    public String price;
    public String label;
    public String contact;     
    public boolean walkinAvailable; 
    public UserAccount owner; 
    public boolean reservationAvailable;
    public ArrayList<String> reservations = new ArrayList<>();

    // Updated constructor to require the Database ID
    public Place(int id, String name, String desc, String loc, UserAccount owner, String label, 
                 boolean res, boolean walk, String con, String price) {
        this.id = id;
        this.name = name;
        this.description = desc; 
        this.location = loc;     
        this.owner = owner;
        this.label = label;
        this.reservationAvailable = res;
        this.walkinAvailable = walk; 
        this.contact = con;       
        this.price = price;
    }
}