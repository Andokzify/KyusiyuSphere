package kyusiyusphere;

public class Place {
    public int id;
    public String name;
    public String description;
    public String location;
    public UserAccount owner; 
    public String label;
    public boolean reservationAvailable;
    public boolean walkinAvailable;
    public String contact;
    public double price; 
    public int maxReservations; 

    public Place(int id, String name, String description, String location, UserAccount owner, 
                 String label, boolean resAvail, boolean walkinAvail, String contact, 
                 double price, int maxReservations) {
        
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.owner = owner;
        this.label = label;
        this.reservationAvailable = resAvail;
        this.walkinAvailable = walkinAvail;
        this.contact = contact;
        this.price = price;
        this.maxReservations = maxReservations;
    }
}