package kyusiyusphere;

public class UserAccount {
    public int id;
    public String name;
    public String email;
    public String password;
    public int role;
    public String campus;
    public String profilePicPath; 

    public UserAccount(int id, String name, String email, String password, int role, String campus, String profilePicPath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.campus = campus;
        this.profilePicPath = profilePicPath; 
    }
}