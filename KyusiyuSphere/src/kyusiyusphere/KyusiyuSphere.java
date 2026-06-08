package kyusiyusphere;

import java.util.ArrayList;

/**
 * Main Controller for KyusiyuSphere.
 * Handles the launch sequence and routes users to their specific GUI dashboards.
 */
public class KyusiyuSphere {
    
    public static int userLoginCount = 0;
    public static int ownerLoginCount = 0;
    public static int adminLoginCount = 0;

    static ArrayList<String> suggestions = new ArrayList<>();

    public static void main(String[] args) {
        // 📍 THE CRITICAL FIX: Ensure tables exist before the GUI opens
        DatabaseHelper.initializeDatabase();

        java.awt.EventQueue.invokeLater(() -> {
            new StartingScreen().setVisible(true); 
        });
    }

    /* ================= SECURITY & VALIDATION ================= */
    
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        int letterCount = 0;
        int numberCount = 0;
        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) letterCount++;
            else if (Character.isDigit(ch)) numberCount++;
        }
        return letterCount >= 2 && numberCount >= 2;
    }

    /* ================= GUI DASHBOARD ROUTING ================= */
    
    public static void userMenu(UserAccount user) {
        userLoginCount++;
        Session.login(user); // Sync the global session
        java.awt.EventQueue.invokeLater(() -> new UserFrame(user).setVisible(true));
    }

    public static void ownerMenu(UserAccount owner) {
        ownerLoginCount++;
        Session.login(owner); // Sync the global session
        java.awt.EventQueue.invokeLater(() -> new OwnerFrame().setVisible(true));
    }

    public static void adminMenu(UserAccount admin) {
        adminLoginCount++;
        Session.login(admin); // Sync the global session
        java.awt.EventQueue.invokeLater(() -> new AdminDashboard().setVisible(true));
    }

    /* ================= REGISTRATION BRIDGE ================= */

    public static void registerFromGUI(String name, String email, String pass, int role, String campus) {
        if (!isValidPassword(pass)) {
            System.err.println("Registration rejected: Password policy violation.");
            return;
        }
        
        // Matches the 7-parameter UserAccount constructor[cite: 8]
        UserAccount newUser = new UserAccount(0, name, email, pass, role, campus, null);
        System.out.println("New User Instance Created: " + newUser.name);
    }
} 